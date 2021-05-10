package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminInterfaceController implements Initializable {

    @FXML
    private Button exitButton;
    @FXML
    private ImageView adminGUIBackgroundImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backgroundFile = new File("images/background.jpg");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        adminGUIBackgroundImageView.setImage(backgroundImage);
    }

    @FXML
    void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }


}
