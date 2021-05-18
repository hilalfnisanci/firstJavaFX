package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserInterfaceController implements Initializable{

    @FXML
    private Button viewProfileButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userMailLabel;

    @FXML
    private Label userCountryLabel;

    @FXML
    private Label subsTypeLabel;

    @FXML
    private Label paymentLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button popListButton;

    @FXML
    private Button jazzListButton;

    @FXML
    private Button classicListButton;

    @FXML
    private Button pauseButton;

    @FXML
    private ImageView pauseButtonImageView;

    @FXML
    private Button backButton;

    @FXML
    private ImageView backButtonImageView;

    @FXML
    private Button nextButton;

    @FXML
    private ImageView nextButtonImageView;

    @FXML
    private Label musicNameLabel;

    @FXML
    private ImageView musicIconImageView;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private Label playlistLabel;

    @FXML
    private TableView<ModelTable> playlistTable;

    @FXML
    private TableColumn<ModelTable, String> sutunBaslik;

    @FXML
    private TableColumn<ModelTable, String> sutunSanatci;

    @FXML
    private TableColumn<ModelTable, String> sutunAlbum;

    @FXML
    private TableColumn<ModelTable, String> sutunTarih;

    @FXML
    private TableColumn<ModelTable, String> sutunDinlenme;

    ObservableList<ModelTable> popList = FXCollections.observableArrayList();
    ObservableList<ModelTable> jazzList = FXCollections.observableArrayList();
    ObservableList<ModelTable> classicList = FXCollections.observableArrayList();

    @FXML
    void popButtonOnAction(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String command = "select m.musicName, s.singerName, a.albumName, m.date, m.numofListening \n" +
                "from albumtable a, musictable m, singertable s , lists l\n" +
                "where a.musicID = m.musicID and s.singerID = m.singerID and l.musicID = m.musicID and m.typeID = 1 ; ";

        try{
            ResultSet rs = connectDB.createStatement().executeQuery(command);

            while(rs.next()){
                popList.add(new ModelTable(rs.getString("musicName"),rs.getString("singerName"),
                        rs.getString("albumName"),rs.getString("date"),rs.getString("numofListening")));
            }

        }catch (SQLException ex){
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE,null,ex);
        }

        sutunBaslik.setCellValueFactory(new PropertyValueFactory<>("musicName"));
        sutunSanatci.setCellValueFactory(new PropertyValueFactory<>("singerName"));
        sutunAlbum.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        sutunTarih.setCellValueFactory(new PropertyValueFactory<>("date"));
        sutunDinlenme.setCellValueFactory(new PropertyValueFactory<>("numofListening"));

        playlistTable.setItems(popList);
        playlistLabel.setText("Pop Çalma Listesi");
        playlistTable.setVisible(true);
    }

    @FXML
    void jazzButtonOnAction(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String command = "select m.musicName, s.singerName, a.albumName, m.date, m.numofListening \n" +
                "from albumtable a, musictable m, singertable s , lists l\n" +
                "where a.musicID = m.musicID and s.singerID = m.singerID and l.musicID = m.musicID and m.typeID = 2 ; ";

        try{
            ResultSet rs = connectDB.createStatement().executeQuery(command);

            while(rs.next()){
                jazzList.add(new ModelTable(rs.getString("musicName"),rs.getString("singerName"),
                        rs.getString("albumName"),rs.getString("date"),rs.getString("numofListening")));
            }

        }catch (SQLException ex){
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE,null,ex);
        }

        sutunBaslik.setCellValueFactory(new PropertyValueFactory<>("musicName"));
        sutunSanatci.setCellValueFactory(new PropertyValueFactory<>("singerName"));
        sutunAlbum.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        sutunTarih.setCellValueFactory(new PropertyValueFactory<>("date"));
        sutunDinlenme.setCellValueFactory(new PropertyValueFactory<>("numofListening"));

        playlistTable.setItems(jazzList);
        playlistLabel.setText("Jazz Çalma Listesi");
        playlistTable.setVisible(true);
    }

    @FXML
    void classicButtonOnAction(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String command = "select m.musicName, s.singerName, a.albumName, m.date, m.numofListening \n" +
                "from albumtable a, musictable m, singertable s , lists l\n" +
                "where a.musicID = m.musicID and s.singerID = m.singerID and l.musicID = m.musicID and m.typeID = 3 ; ";

        try{
            ResultSet rs = connectDB.createStatement().executeQuery(command);

            while(rs.next()){
                classicList.add(new ModelTable(rs.getString("musicName"),rs.getString("singerName"),
                        rs.getString("albumName"),rs.getString("date"),rs.getString("numofListening")));
            }

        }catch (SQLException ex){
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE,null,ex);
        }

        sutunBaslik.setCellValueFactory(new PropertyValueFactory<>("musicName"));
        sutunSanatci.setCellValueFactory(new PropertyValueFactory<>("singerName"));
        sutunAlbum.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        sutunTarih.setCellValueFactory(new PropertyValueFactory<>("date"));
        sutunDinlenme.setCellValueFactory(new PropertyValueFactory<>("numofListening"));

        playlistTable.setItems(classicList);
        playlistLabel.setText("Klasik Çalma Listesi");
        playlistTable.setVisible(true);
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {

    }

    @FXML
    void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    @FXML
    void nextButtonOnAction(ActionEvent event) {

    }

    @FXML
    void stopPlayButtonOnAction(ActionEvent event) {

    }

    @FXML
    void viewProfileButtonOnAction(ActionEvent event) {
        LoginController lc = new LoginController();
        String user = lc.userName;

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String command = "";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backgroundFile = new File("images/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        backgroundImageView.setImage(backgroundImage);

        File pauseButtonFile = new File("images/pause.png");
        Image pauseButtonImage = new Image(pauseButtonFile.toURI().toString());
        pauseButtonImageView.setImage(pauseButtonImage);

        File backButtonFile = new File("images/back.png");
        Image backButtonImage = new Image(backButtonFile.toURI().toString());
        backButtonImageView.setImage(backButtonImage);

        File nextButtonFile = new File("images/next.png");
        Image nextButtonImage = new Image(nextButtonFile.toURI().toString());
        nextButtonImageView.setImage(nextButtonImage);

        File musicIconFile = new File("images/music_icon.png");
        Image musicIconImage = new Image(musicIconFile.toURI().toString());
        musicIconImageView.setImage(musicIconImage);

        playlistTable.setVisible(false);

    }
}
