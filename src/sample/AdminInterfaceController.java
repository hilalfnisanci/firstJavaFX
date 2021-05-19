package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminInterfaceController implements Initializable {

    @FXML
    private Button exitButton;
    @FXML
    private Button albumButton;
    @FXML
    private Button musicButton;
    @FXML
    private Button singerButton;
    @FXML
    private ImageView adminGUIBackgroundImageView;
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private ChoiceBox<String> actionChoiceBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField name2TextField;
    @FXML
    private Label nameLabel2;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private Label typeLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private TextField name3TextField;
    @FXML
    private Label nameLabel3;
    @FXML
    private TextField timeTextField;
    @FXML
    private Label timeLabel;


    public String action = "";
    public String choice = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backgroundFile = new File("images/adminBackground.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        adminGUIBackgroundImageView.setImage(backgroundImage);
        addItems();
        nameLabel.setVisible(false);
        nameTextField.setVisible(false);
        nameLabel2.setVisible(false);
        name2TextField.setVisible(false);
        nameLabel3.setVisible(false);
        name3TextField.setVisible(false);
        typeLabel.setVisible(false);
        typeTextField.setVisible(false);
        dateLabel.setVisible(false);
        dateTextField.setVisible(false);
        timeLabel.setVisible(false);
        timeTextField.setVisible(false);
    }

    @FXML
    public void albumButtonOnAction(){
        choice = albumButton.getText();
        nameLabel.setText("Şarkıcı Adı:");
        nameLabel2.setText("Albüm Adı: ");
        typeLabel.setText("Tür: ");
        dateLabel.setText("Tarih: ");

        nameLabel.setVisible(true);
        nameTextField.setVisible(true);
        nameLabel2.setVisible(true);
        name2TextField.setVisible(true);
        nameLabel3.setVisible(false);
        name3TextField.setVisible(false);
        typeLabel.setVisible(true);
        typeTextField.setVisible(true);
        dateLabel.setVisible(true);
        dateTextField.setVisible(true);
        timeLabel.setVisible(false);
        timeTextField.setVisible(false);

    }
    @FXML
    public void musicButtonOnAction(){
        choice = musicButton.getText();
        nameLabel.setText("Şarkı Adı: ");
        nameLabel2.setText("Şarkıcı Adı: ");
        nameLabel3.setText("Albüm Adı: ");
        dateLabel.setText("Tarih: ");
        typeLabel.setText("Tür: ");
        timeLabel.setText("Şarkı Süresi (Örn: 3:15)");
        nameLabel.setVisible(true);
        nameTextField.setVisible(true);
        nameLabel2.setVisible(true);
        name2TextField.setVisible(true);
        nameLabel3.setVisible(true);
        name3TextField.setVisible(true);
        dateLabel.setVisible(true);
        dateTextField.setVisible(true);
        typeLabel.setVisible(true);
        typeTextField.setVisible(true);
        timeLabel.setVisible(true);
        timeTextField.setVisible(true);
    }
    @FXML
    public void singerButtonOnAction(){
        choice = singerButton.getText();
        nameLabel.setText("Şarkıcı Adı:");
        nameLabel2.setText("Ülke Adı: ");
        nameLabel.setVisible(true);
        nameTextField.setVisible(true);
        nameLabel2.setVisible(true);
        name2TextField.setVisible(true);
        nameLabel3.setVisible(false);
        name3TextField.setVisible(false);
        typeLabel.setVisible(false);
        typeTextField.setVisible(false);
        dateLabel.setVisible(false);
        dateTextField.setVisible(false);
        timeLabel.setVisible(false);
        timeTextField.setVisible(false);
    }

    @FXML
    public void okButtonOnAction(){
        System.out.println("kontrol");
        String sql="";
        if(actionChoiceBox.getSelectionModel().getSelectedItem().equals("Ekle")){

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            if(choice.equals("Sanatçı")){
                String singerName =nameTextField.getText();
                String countryID = name2TextField.getText();
                int id = findCountryID(countryID);

                sql = "INSERT INTO singertable(singerName,countryID) values(" + "'"+singerName+"'," + id +");";
            }else if(choice.equals("Albüm")){
                String singerName = nameTextField.getText();
                String albumName = name2TextField.getText();
                String typeName = typeTextField.getText();
                String date = dateTextField.getText();

                sql = "insert into albumtable(albumID,singerID,albumName,typeID,date) " +
                        "values("+43+","+findSingerID(singerName)+",'"+albumName+"',"+findTypeID(typeName)+",'"+date+"');";

            }else{
                String musicName = nameTextField.getText();
                String singerName = name2TextField.getText();
                String albumName = name3TextField.getText();
                String typeName = typeTextField.getText();
                String date = dateTextField.getText();
                String time = timeTextField.getText();

                sql = "INSERT INTO musictable(musicName,singerID,albumID,typeID,time,date,numofListening) " +
                "values('"+musicName+"'," +findSingerID(singerName)+","+findAlbumID(albumName)+","+findTypeID(typeName)+",'"+time+"','"+date+"',"+0+");";
            }

            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(sql);
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
        else if(actionChoiceBox.getSelectionModel().getSelectedItem().equals("Sil")){
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            if(choice.equals("Sanatçı")){
                String singerName =nameTextField.getText();
                sql = "delete from singertable where singerID = "+ findSingerID(singerName) +";";
            }else if(choice.equals("Albüm")){
                String albumName = name2TextField.getText();
                sql = "delete from albumtable where albumID ="+ findAlbumID(albumName) +";";

            }else{
                String musicName = nameTextField.getText();

                sql = "delete from musictable where musicName = '"+musicName+"'"+";";
            }

            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(sql);
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
        else{
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            if(choice.equals("Sanatçı")){
                String singerName =nameTextField.getText();
                sql = "update from singertable where singerID = "+ findSingerID(singerName) +";";
            }else if(choice.equals("Albüm")){
                String albumName = name2TextField.getText();
                sql = "update from albumtable where albumID ="+ findAlbumID(albumName) +";";

            }else{
                String musicName = nameTextField.getText();

                sql = "update from musictable where musicID = "+findMusicID(musicName)+";";
            }
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(sql);
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }

    }
    public void addItems(){
        actionChoiceBox.getItems().add("Ekle");
        actionChoiceBox.getItems().add("Sil");
        actionChoiceBox.getItems().add("Güncelle");
    }

    @FXML
    public void onMouseClick(){

        if(actionChoiceBox.getSelectionModel().getSelectedItem() != null){
            action = actionChoiceBox.getSelectionModel().getSelectedItem();
            //System.out.println(choice);
        }
    }

    @FXML
    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
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

    public int findAlbumID(String albumName){
        int albumID = 0;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql = "select a.albumID from albumtable a where albumName = '" + albumName + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next())
                albumID = rs.getInt("albumID");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return albumID;
    }

    public int findSingerID(String singerName){
        int singerID = 0;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql = "select s.singerID from singertable s where singerName = '" + singerName + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next())
                singerID = rs.getInt("singerID");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return singerID;
    }

    public int findTypeID(String typeName){
        int id=0;
        if(typeName.equals("pop"))
            id=1;
        else if(typeName.equals("jazz"))
            id=2;
        else id=3;
        return id;
    }

    public int findMusicID(String musicName){
        int musicID = 0;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String sql = "select m.musicID from musictable m where musicName = '" + musicName + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next())
                musicID = rs.getInt("musicID");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return musicID;
    }
}
