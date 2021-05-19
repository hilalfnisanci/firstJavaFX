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
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminLoginController implements Initializable {

    @FXML
    private TextField adminUsernameTextField;
    @FXML
    private PasswordField adminPasswordTextField;
    @FXML
    private ImageView adminUserImageView;
    @FXML
    private ImageView adminPasswordImageView;
    @FXML
    private Button adminLoginButton;
    @FXML
    private Button adminCancelButton;
    @FXML
    private Label adminLoginMessageLabel;
    @FXML
    private Button adminSignUpButton;
    @FXML
    private ImageView adminIconImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File adminIconFile = new File("images/adminIcon.png");
        Image adminIconImage = new Image(adminIconFile.toURI().toString());
        adminIconImageView.setImage(adminIconImage);

        File adminUserFile = new File("images/adminUsername.png");
        Image adminUserImage = new Image(adminUserFile.toURI().toString());
        adminUserImageView.setImage(adminUserImage);

        File adminPasswordFile = new File("images/password.png");
        Image adminPasswordImage = new Image(adminPasswordFile.toURI().toString());
        adminPasswordImageView.setImage(adminPasswordImage);

    }

    @FXML
    public void adminLoginButtonOnAction(){
        if(!adminUsernameTextField.getText().isBlank() && !adminPasswordTextField.getText().isBlank()){
            validateAdminLogin();
            //adminLoginMessageLabel.setText("Congratulations!");
        }else{
            adminLoginMessageLabel.setText("Please enter username and password!");
        }
    }

    @FXML
    public void adminCancelButtonOnAction(){
        Stage stage = (Stage) adminCancelButton.getScene().getWindow();
        stage.close();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
            Stage loginStage = new Stage();
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setScene(new Scene(root, 720, 420));
            loginStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void adminSignUpButtonOnAction(){
        Stage stage = (Stage) adminSignUpButton.getScene().getWindow();
        stage.close();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminRegister.fxml")));
            Stage loginStage = new Stage();
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setScene(new Scene(root, 520, 546));
            loginStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    public void  validateAdminLogin(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM admintable WHERE username = '" + adminUsernameTextField.getText() + "' AND password ='" + adminPasswordTextField.getText() +"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1)==1){
                    createAdminGUI();
                    //adminLoginMessageLabel.setText("Congratulations!");
                }else{
                    adminLoginMessageLabel.setText("Invalid login. Please try again!!");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createAdminGUI(){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminInterface.fxml")));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 1120, 700));
            registerStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
