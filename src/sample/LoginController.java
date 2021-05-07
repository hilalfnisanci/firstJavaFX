package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView iconImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private ImageView userImageView;
    @FXML
    private ImageView passwordImageView;
    @FXML
    private Button signUpButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("images/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File iconFile = new File("images/loginIcon.png");
        Image iconImage = new Image(iconFile.toURI().toString());
        iconImageView.setImage(iconImage);

        File userFile = new File("images/user.png");
        Image userImage = new Image(userFile.toURI().toString());
        userImageView.setImage(userImage);

        File passwordFile = new File("images/password.png");
        Image passwordImage = new Image(passwordFile.toURI().toString());
        passwordImageView.setImage(passwordImage);

    }

    public void loginButtonOnAction(ActionEvent event){

        if(!usernameTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()){
            //validateLogin();
            loginMessageLabel.setText("Congratulations!");
        }else{
            loginMessageLabel.setText("Please enter username and password!");
        }
    }

    public void signUpButtonAction(ActionEvent event){
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();
        createAccountField();
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + usernameTextField.getText() + "' AND password ='" + enterPasswordField.getText() +"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1)==1){
                    createAccountField();
                }else{
                    loginMessageLabel.setText("Invalid login. Please try again!!");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public void createAccountField(){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 502));
            registerStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


}
