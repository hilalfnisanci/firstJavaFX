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
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminRegisterController implements Initializable {

    @FXML
    private ImageView shieldImageView;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Button adminRegisterButton;
    @FXML
    private Button cancelButton;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField mailTextField;
    @FXML
    private TextField uniqueCodeTextField;
    @FXML
    private TextField countryTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File shieldFile = new File("images/shield.png");
        Image shieldImage = new Image(shieldFile.toURI().toString());
        shieldImageView.setImage(shieldImage);
    }

    String uniqueCode = "Uniq@123";

    @FXML
    void adminCancelButtonOnAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
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
    void adminRegisterButtonOnAction() {
        if(!firstnameTextField.getText().isEmpty() && !lastnameTextField.getText().isEmpty() && !usernameTextField.getText().isEmpty() &&
                !setPasswordField.getText().isEmpty() && !mailTextField.getText().isEmpty() && !countryTextField.getText().isEmpty() && uniqueCodeTextField.getText().equals(uniqueCode)){
            if(setPasswordField.getText().equals(confirmPasswordField.getText())){
                registerAdmin();
                confirmPasswordLabel.setText("");

            }else{
                registrationMessageLabel.setText("");
                confirmPasswordLabel.setText("Password does not match!");
            }
        }else{
            registrationMessageLabel.setText("Please fill in all information! ");
        }
    }

    public void registerAdmin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();
        String mail = mailTextField.getText();
        String country = countryTextField.getText();

        int result = Integer.parseInt(country);

        String insertFields = "INSERT INTO admintable (firstname, lastname, username, mail, password, countryID) VALUES('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + mail + "','" + password + "'," + result + ")";
        String insertToRegister = insertFields+insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("User has been registered successfully!");
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

}
