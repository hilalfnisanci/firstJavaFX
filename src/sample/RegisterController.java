package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class RegisterController implements Initializable {

    @FXML
    private ImageView shieldImageView;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Button registerButton;
    @FXML
    private Button closeButton;
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
    private TextField countryTextField;
    @FXML
    private CheckBox premiumCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File shieldFile = new File("images/shield.png");
        Image shieldImage = new Image(shieldFile.toURI().toString());
        shieldImageView.setImage(shieldImage);
    }

    @FXML
    public void registerButtonOnAction(){

        if(!firstnameTextField.getText().isEmpty() && !lastnameTextField.getText().isEmpty() && !usernameTextField.getText().isEmpty() &&
                !setPasswordField.getText().isEmpty() && !mailTextField.getText().isEmpty() && !countryTextField.getText().isEmpty()){
            if(setPasswordField.getText().equals(confirmPasswordField.getText())){
                registerUser();
                confirmPasswordLabel.setText("");

            }else{
                registrationMessageLabel.setText("");
                confirmPasswordLabel.setText("Password does not match!");
            }
        }else{
            registrationMessageLabel.setText("Please fill in all information! ");
        }

    }

    @FXML
    public void closeButtonOnAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
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

    public void registerUser(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();
        String mail = mailTextField.getText();
        String country = countryTextField.getText();
        boolean isSelected = premiumCheckBox.isSelected();

        int result = findCountryID(country);

        String insertFields = "INSERT INTO usertable (firstname, lastname, username, password, mail, countryID,userTypeID,paidup) VALUES('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "','" + mail + "'," + result +"," + isSelected + ","+ 0 + ")";
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

    public int findCountryID(String countryName){

        int countryID = 0;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql = "select c.countryID from countrytable c where countryName = '" + countryName + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next())
                countryID = rs.getInt("countryID");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return countryID;
    }
}
