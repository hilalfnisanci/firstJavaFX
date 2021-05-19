package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserInterfaceController implements Initializable{

    @FXML
    private Button userPopButton;
    @FXML
    private Button userJazzButton;
    @FXML
    private Button userKlasikButton;
    @FXML
    private Button viewProfileButton;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userMailLabel;
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
    private Button top10Button;
    @FXML
    private Button pauseButton;
    @FXML
    private ImageView pauseButtonImageView;
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
    @FXML
    private Tab musicTab;
    @FXML
    private TableView<ModelTableMusic> musicTable;
    @FXML
    private TableColumn<ModelTableMusic, String> musicTabBaslik;
    @FXML
    private TableColumn<ModelTableMusic, String> musicTabSanatci;
    @FXML
    private Tab albumTab;
    @FXML
    private TableView<ModelTableAlbum> albumTable;
    @FXML
    private TableColumn<ModelTableAlbum, String> albumTabAdi;
    @FXML
    private TableColumn<ModelTableAlbum, String> albumTabSanatci;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button backFromListButton;
    @FXML
    private TableView<ModelTableUsers> usersTable;
    @FXML
    private TableColumn<ModelTableUsers, String> userName;
    @FXML
    private Button followButton;
    @FXML
    private ImageView followIconImageView;

    ObservableList<ModelTable> popList = FXCollections.observableArrayList();
    ObservableList<ModelTable> jazzList = FXCollections.observableArrayList();
    ObservableList<ModelTable> classicList = FXCollections.observableArrayList();
    ObservableList<ModelTable> top10List = FXCollections.observableArrayList();
    ObservableList<ModelTable> userPopList = FXCollections.observableArrayList();
    ObservableList<ModelTable> userJazzList = FXCollections.observableArrayList();
    ObservableList<ModelTable> userKlasikList = FXCollections.observableArrayList();
    ObservableList<ModelTableUsers> userList = FXCollections.observableArrayList();
    ObservableList<ModelTableMusic> musicTableList = FXCollections.observableArrayList();
    ObservableList<ModelTableAlbum> albumTableList = FXCollections.observableArrayList();
    public String followerName;

    @FXML
    void backFromListButtonOnAction(){
        playlistTable.setVisible(false);
        tabPane.setVisible(true);
        playlistLabel.setText("Müzikler");
    }

    @FXML
    void top10ButtonOnAction(){
        playlistTable.getItems().clear();
        tabPane.setVisible(false);
        top10List.removeAll();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String command = "select m.musicName ,s.singerName, a.albumName, m.date, m.numofListening\n" +
                "from musictable m, playlisttable p, singertable s, albumtable a\n" +
                "where m.typeID = 1 and p.typeID = 1 and s.singerID = m.singerID and m.albumID = a.albumID and s.countryID = 4\n" +
                "order by m.numofListening desc limit 0,10;";

        try{
            ResultSet rs = connectDB.createStatement().executeQuery(command);

            while(rs.next()){
                top10List.add(new ModelTable(rs.getString("musicName"),rs.getString("singerName"),
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

        playlistTable.setItems(top10List);
        playlistLabel.setText("Top 10 Listesi");
        playlistTable.setVisible(true);
    }

    @FXML
    void popButtonOnAction(ActionEvent event) {
        playlistTable.getItems().clear();
        tabPane.setVisible(false);
        popList.removeAll();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String command = "select distinct m.musicName ,s.singerName, a.albumName, m.date, m.numofListening\n" +
                "from musictable m, playlisttable p, singertable s, albumtable a\n" +
                "where m.typeID = 1 and p.typeID = 1 and s.singerID = m.singerID and m.albumID = a.albumID\n" +
                "order by m.numofListening desc limit 0,10;";

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
        playlistLabel.setText("Top 10 Pop Listesi");
        playlistTable.setVisible(true);
    }

    @FXML
    void jazzButtonOnAction(ActionEvent event) {
        playlistTable.getItems().clear();
        tabPane.setVisible(false);
        jazzList.removeAll();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String command = "select distinct m.musicName ,s.singerName, a.albumName, m.date, m.numofListening\n" +
                "from musictable m, playlisttable p, singertable s, albumtable a\n" +
                "where m.typeID = 2 and p.typeID = 2 and s.singerID = m.singerID and m.albumID = a.albumID\n" +
                "order by m.numofListening desc limit 0,10;";


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
        playlistLabel.setText("Top 10 Jazz Listesi");
        playlistTable.setVisible(true);
    }

    @FXML
    void classicButtonOnAction(ActionEvent event) {
        playlistTable.getItems().clear();
        tabPane.setVisible(false);
        classicList.removeAll();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String command = "select distinct m.musicName ,s.singerName, a.albumName, m.date, m.numofListening\n" +
                "from musictable m, playlisttable p, singertable s, albumtable a\n" +
                "where m.typeID = 3 and p.typeID = 3 and s.singerID = m.singerID and m.albumID = a.albumID\n" +
                "order by m.numofListening desc limit 0,10;";

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
        playlistLabel.setText("Top 10 Klasik Listesi");
        playlistTable.setVisible(true);
    }



    @FXML
    void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }



    @FXML
    void stopPlayButtonOnAction(ActionEvent event) {
        musicNameLabel.setText("");
        System.out.println("müzik durdu");
    }

    @FXML
    void viewProfileButtonOnAction(ActionEvent event) {
        userNameLabel.setText("Name: "+ LoginController.userInfo.get(0));
        userMailLabel.setText("e-mail: "+LoginController.userInfo.get(1));
        subsTypeLabel.setText("Aboelik Türü: "+LoginController.userInfo.get(2));
        if(LoginController.userInfo.get(2) == "Premium")
            paymentLabel.setText("Ödeme Bilgisi: "+LoginController.userInfo.get(3));
    }

    @FXML
    void addMusicButtonOnAction(){

        if(musicNameLabel.getText() != null){
            String username = (String) LoginController.userInfo.get(0);
            String musicName = musicNameLabel.getText();
            int musicID=0,userID=0,typeID = 0,playlistID=0;

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String sql = "select m.musicID,m.typeID from musicTable m where musicName="+"\""+musicName+"\""+";";
            try{
                Statement statement = connectDB.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while(rs.next()){
                    musicID = rs.getInt("musicID");
                    typeID = rs.getInt("typeID");
                }
                rs.close();

            }catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
            System.out.println("music id: "+musicID+" \ntype id:"+typeID);
            sql = "select u.userID from usertable u where u.username ="+"'" +username+"';";
            try{
                Statement statement = connectDB.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while(rs.next()){
                    userID = rs.getInt("userID");
                }
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
            System.out.println("user id:"+userID);
            sql = "select p.playlistID from playlisttable p where p.userID = "+ userID +" and p.typeID = "+typeID+";";
            try{
                Statement statement = connectDB.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while(rs.next()){
                    playlistID = rs.getInt("playlistID");
                }
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
            System.out.println("playlist id:"+playlistID);
            sql = "insert into lists(musicID,playlistID) values("+musicID+","+playlistID+");";
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(sql);
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }


    }

    @FXML
    void musicTabOnSelection(){
        musicTable.getItems().clear();
        musicTableList.removeAll();

        takeMusicData();
    }

    @FXML
    void albumTabOnSelection(){
        albumTable.getItems().clear();
        albumTableList.removeAll();
        takeAlbumData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File backgroundFile = new File("images/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        backgroundImageView.setImage(backgroundImage);

        File pauseButtonFile = new File("images/pause.png");
        Image pauseButtonImage = new Image(pauseButtonFile.toURI().toString());
        pauseButtonImageView.setImage(pauseButtonImage);

        File musicIconFile = new File("images/music_icon.png");
        Image musicIconImage = new Image(musicIconFile.toURI().toString());
        musicIconImageView.setImage(musicIconImage);

        File followIconFile = new File("images/followIcon.png");
        Image followIconImage = new Image(followIconFile.toURI().toString());
        followIconImageView.setImage(followIconImage);

        playlistTable.setVisible(false);
        showUsers();

    }

    public void takeMusicData(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql = "select m.musicName, s.singerName from musictable m, singertable s where m.singerID = s.singerID;";
        try{
            ResultSet rs = connectDB.createStatement().executeQuery(sql);

            while(rs.next()){
                musicTableList.add(new ModelTableMusic(rs.getString("musicName"),rs.getString("singerName")));
            }

        }catch (SQLException ex){
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE,null,ex);
        }
        musicTabBaslik.setCellValueFactory(new PropertyValueFactory<>("musicName"));
        musicTabSanatci.setCellValueFactory(new PropertyValueFactory<>("singerName"));

        musicTable.setItems(musicTableList);
        playlistLabel.setText("Müzikler");
        playlistTable.setVisible(false);
    }

    public void takeAlbumData(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql = "select distinct a.albumName, s.singerName from albumtable a, singertable s where a.singerID = s.singerID;";
        try{
            ResultSet rs = connectDB.createStatement().executeQuery(sql);

            while(rs.next()){
                albumTableList.add(new ModelTableAlbum(rs.getString("albumName"),rs.getString("singerName")));
            }

        }catch (SQLException ex){
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE,null,ex);
        }
        albumTabAdi.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        albumTabSanatci.setCellValueFactory(new PropertyValueFactory<>("singerName"));

        albumTable.setItems(albumTableList);
        playlistLabel.setText("Albümler");
        playlistTable.setVisible(false);
    }

    public void showUsers(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql = "SELECT u.username from usertable u where u.userTypeID = 1;";
        try{
            ResultSet rs = connectDB.createStatement().executeQuery(sql);
            while(rs.next()){
                userList.add(new ModelTableUsers(rs.getString("username")));
            }

        }catch (SQLException ex){
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE,null,ex);
        }

        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        usersTable.setItems(userList);
    }
    @FXML
    public void musicTableOnMouseClick(){

        if(musicTable.getSelectionModel().getSelectedItem() != null){
            String musicName = musicTable.getSelectionModel().getSelectedItem().getMusicName();
            musicNameLabel.setText(musicName);

        }
    }

    @FXML
    public void playlistTableOnMouseClick(){
        if(playlistTable.getSelectionModel().getSelectedItem() != null){
            String musicName = playlistTable.getSelectionModel().getSelectedItem().getMusicName();
            musicNameLabel.setText(musicName);
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            int id= findMusicID(musicName);
            int num = Integer.parseInt(playlistTable.getSelectionModel().getSelectedItem().getNumofListening());
            num = num+1;
            String sql = "update musictable set numofListening = "+ num+"where musicID = "+id+";";

            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(sql);
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    @FXML
    public void followButtonOnAction(){
        if(LoginController.userInfo.get(2) == "Premium"){
            int userID = 0,followerID=0;
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String sql= "select u.userID from usertable u where u.username =" + "'" +LoginController.userInfo.get(0) + "';";
            try{
                Statement statement = connectDB.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while(rs.next()){
                    userID = rs.getInt("userID");
                }
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

            sql = "select u.userID from usertable u where u.username = "+"'"+followerName+"';";

            try{
                Statement statement = connectDB.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while(rs.next()){
                    followerID = rs.getInt("userID");
                }
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

            sql = "insert into followtable(userID,followerID) values("+userID+","+ followerID +");";
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(sql);
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }

        }
    }

    @FXML
    public void followTableOnMouseClick(){
        if(usersTable.getSelectionModel().getSelectedItem() != null){
            followerName = usersTable.getSelectionModel().getSelectedItem().getUserName();
        }
    }

    @FXML
    void userJazzButtonOnAction(ActionEvent event) {
        playlistTable.getItems().clear();
        tabPane.setVisible(false);
        userJazzList.removeAll();
        int userID=0;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql= "select u.userID from usertable u where u.username =" + "'" +LoginController.userInfo.get(0) + "';";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                userID = rs.getInt("userID");
            }
            rs.close();
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        sql = "select distinct m.musicName ,s.singerName, a.albumName, m.date, m.numofListening " +
                "from musictable m, playlisttable p, singertable s, albumtable a,lists l" +
                " where p.userID = "+userID+" and m.musicID = l.musicID and m.albumID=a.albumID and l.playlistID = p.playlistID " +
                "and p.typeID = 2 and m.singerID = s.singerID;";
        try{
            ResultSet rs = connectDB.createStatement().executeQuery(sql);

            while(rs.next()){
                userJazzList.add(new ModelTable(rs.getString("musicName"),rs.getString("singerName"),
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

        playlistTable.setItems(userJazzList);
        playlistLabel.setText("Jazz Çalma Listesi");
        playlistTable.setVisible(true);
    }

    @FXML
    void userKlasikButtonOnAction(ActionEvent event) {
        playlistTable.getItems().clear();
        tabPane.setVisible(false);
        userKlasikList.removeAll();
        int userID=0;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql= "select u.userID from usertable u where u.username =" + "'" +LoginController.userInfo.get(0) + "';";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                userID = rs.getInt("userID");
            }
            rs.close();
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        sql = "select distinct m.musicName ,s.singerName, a.albumName, m.date, m.numofListening " +
                "from musictable m, playlisttable p, singertable s, albumtable a,lists l" +
                " where p.userID = "+userID+" and m.musicID = l.musicID and m.albumID=a.albumID and l.playlistID = p.playlistID " +
                "and p.typeID = 3 and m.singerID = s.singerID;";
        try{
            ResultSet rs = connectDB.createStatement().executeQuery(sql);

            while(rs.next()){
                userKlasikList.add(new ModelTable(rs.getString("musicName"),rs.getString("singerName"),
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

        playlistTable.setItems(userKlasikList);
        playlistLabel.setText("Klasik Çalma Listesi");
        playlistTable.setVisible(true);
    }

    @FXML
    void userPopButtonOnAction(ActionEvent event) {
        playlistTable.getItems().clear();
        tabPane.setVisible(false);
        userPopList.removeAll();
        int userID=0;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String sql= "select u.userID from usertable u where u.username =" + "'" +LoginController.userInfo.get(0) + "';";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                userID = rs.getInt("userID");
            }
            rs.close();
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        sql = "select distinct m.musicName ,s.singerName, a.albumName, m.date, m.numofListening " +
                "from musictable m, playlisttable p, singertable s, albumtable a,lists l" +
                " where p.userID = "+userID+" and m.musicID = l.musicID and m.albumID=a.albumID and l.playlistID = p.playlistID " +
                "and p.typeID = 1 and m.singerID = s.singerID;";
        try{
            ResultSet rs = connectDB.createStatement().executeQuery(sql);

            while(rs.next()){
                userPopList.add(new ModelTable(rs.getString("musicName"),rs.getString("singerName"),
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

        playlistTable.setItems(userPopList);
        playlistLabel.setText("Pop Çalma Listesi");
        playlistTable.setVisible(true);

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
