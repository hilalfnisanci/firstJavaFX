package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserInterfaceController implements Initializable {

    public static ArrayList popMusicList = new ArrayList<>();
    public static ArrayList jazzMusicList = new ArrayList<>();
    public static ArrayList classicMusicList = new ArrayList<>();

    static int buttonValue = 0;
    static int stopButtonValue = 0;
    static int profileButtonValue=0;
    static String typeofMusic;

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
    private Label playlistLabel;
    @FXML
    private Label music1Label;
    @FXML
    private Label music2Label;
    @FXML
    private Label music3Label;
    @FXML
    private Label music4Label;
    @FXML
    private Label music5Label;
    @FXML
    private Label musicNameLabel;
    @FXML
    private Label singer1Label;
    @FXML
    private Label singer2Label;
    @FXML
    private Label singer3Label;
    @FXML
    private Label singer4Label;
    @FXML
    private Label singer5Label;
    @FXML
    private Label album1Label;
    @FXML
    private Label album2Label;
    @FXML
    private Label album3Label;
    @FXML
    private Label album4Label;
    @FXML
    private Label album5Label;
    @FXML
    private Label date1Label;
    @FXML
    private Label date2Label;
    @FXML
    private Label date3Label;
    @FXML
    private Label date4Label;
    @FXML
    private Label date5Label;
    @FXML
    private Label listener1Label;
    @FXML
    private Label listener2Label;
    @FXML
    private Label listener3Label;
    @FXML
    private Label listener4Label;
    @FXML
    private Label listener5Label;
    @FXML
    private Button exitButton;
    @FXML
    private Button viewProfileButton;
    @FXML
    private Button popListButton;
    @FXML
    private Button jazzListButton;
    @FXML
    private Button classicListButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button music1Button;
    @FXML
    private Button music2Button;
    @FXML
    private Button music3Button;
    @FXML
    private Button music4Button;
    @FXML
    private Button music5Button;
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private ImageView pauseButtonImageView;
    @FXML
    private ImageView backButtonImageView;
    @FXML
    private ImageView nextButtonImageView;
    @FXML
    private ImageView musicIconImageView;
    @FXML
    private ImageView icon1;
    @FXML
    private ImageView icon2;
    @FXML
    private ImageView icon3;
    @FXML
    private ImageView icon4;
    @FXML
    private ImageView icon5;

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

        File icon1File = new File("images/play.png");
        Image icon1Image = new Image(icon1File.toURI().toString());
        icon1.setImage(icon1Image);

        File icon2File = new File("images/play.png");
        Image icon2Image = new Image(icon2File.toURI().toString());
        icon2.setImage(icon2Image);

        File icon3File = new File("images/play.png");
        Image icon3Image = new Image(icon3File.toURI().toString());
        icon3.setImage(icon3Image);

        File icon4File = new File("images/play.png");
        Image icon4Image = new Image(icon4File.toURI().toString());
        icon4.setImage(icon4Image);

        File icon5File = new File("images/play.png");
        Image icon5Image = new Image(icon5File.toURI().toString());
        icon5.setImage(icon5Image);

    }

    @FXML
    public void exitButtonOnAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    @FXML
    public void backButtonOnAction() {

    }

    @FXML
    public void jazzButtonOnAction() {

    }

    @FXML
    public void classicButtonOnAction() {

    }

    @FXML
    public void popButtonOnAction() {
        getDataPop();
        music1Label.setText((String) popMusicList.get(1));
        music2Label.setText((String) popMusicList.get(5));
        music3Label.setText((String) popMusicList.get(9));
        music4Label.setText((String) popMusicList.get(13));
        music5Label.setText((String) popMusicList.get(17));
        typeofMusic = "pop";
    }

    @FXML
    public void music1ButtonOnAction() {

    }

    @FXML
    public void music2ButtonOnAction() {

    }

    @FXML
    public void music3ButtonOnAction() {

    }

    @FXML
    public void music4ButtonOnAction() {

    }

    @FXML
    public void music5ButtonOnAction() {

    }

    @FXML
    public void nextButtonOnAction() {

    }

    @FXML
    public void stopPlayButtonOnAction() {

    }

    @FXML
    public void viewProfileButtonOnAction() {

    }

    public void getDataPop(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT *FROM music_table WHERE music_type= 'pop'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                int music_id  = queryResult.getInt("musicId");
                String music_name = queryResult.getString("music_name");
                String music_date = queryResult.getString("date");
                String music_singer = queryResult.getString("singer");

                popMusicList.add(music_id);
                popMusicList.add(music_name);
                popMusicList.add(music_date);
                popMusicList.add(music_singer);
            }
            queryResult.close();


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }


}
