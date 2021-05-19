package sample;

import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
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
    @FXML
    private Button loginAdminButton;

    public String userName;

    public static ArrayList userInfo = new ArrayList<>();

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

    @FXML
    public void loginButtonOnAction(){

        if(!usernameTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()){
            validateLogin();
            userDataInfo();
            //loginMessageLabel.setText("Congratulations!");
        }else{
            loginMessageLabel.setText("Please enter username and password!");
        }
    }

    @FXML
    public void signUpButtonOnAction(){
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.close();
        createAccountField();
    }

    @FXML
    public void cancelButtonOnAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    @FXML
    void loginAdminButtonOnAction() {
        Stage stage = (Stage) loginAdminButton.getScene().getWindow();
        stage.close();
        adminLoginGui();
    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM usertable WHERE username = '" + usernameTextField.getText() + "' AND password ='" + enterPasswordField.getText() +"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1)==1){
                    userName = usernameTextField.getText();
                    createUserInterface();
                    //loginMessageLabel.setText("Congratulations!");
                }else{
                    loginMessageLabel.setText("Invalid login. Please try again!!");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public void createUserInterface(){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("userInterface.fxml")));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 1120, 700));
            registerStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void adminLoginGui(){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminLogin.fxml")));
            Stage adminStage = new Stage();
            adminStage.initStyle(StageStyle.UNDECORATED);
            adminStage.setScene(new Scene(root, 520, 502));
            adminStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createAccountField(){

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register.fxml")));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 546));
            registerStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void userDataInfo(){
        userInfo.clear();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql ="SELECT * FROM usertable WHERE username= '" + usernameTextField.getText() + "'" ;

        try{
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                int userID  = rs.getInt("userID");
                String username = rs.getString("username");
                String email = rs.getString("mail");
                int userTypeID  = rs.getInt("userTypeID");
                boolean paidUp = rs.getBoolean("paidup");

                String userType;
                String payment;
                if(userTypeID == 1)
                    userType = "Premium";
                else userType = "Normal";

                if(paidUp)
                    payment = "Ödendi";
                else payment = "Ödenmedi";

                userInfo.add(username);
                userInfo.add(email);
                userInfo.add(userType);
                userInfo.add(payment);

            }

            rs.close();

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }


    }

}
