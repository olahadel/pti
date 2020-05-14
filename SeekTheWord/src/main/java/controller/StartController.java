package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Player;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartController {

    private static Logger logger = Logger.getLogger(StartController.class.getName());

    @FXML
    private Pane startScenePane;

    @FXML
    private TextField playerName;

    @FXML
    private Button startButton;

    public static String savePlayerName;

    @FXML
    public void initialize() {
        startButton.setDefaultButton(true);
        String style = getClass().getResource("/css/Button.css").toExternalForm();
        startScenePane.getStylesheets().add(style);
        startButton.getStylesheets().add(style);
        startButton.getStyleClass().add("shiny-orange");
    }

    public void startTheGame(ActionEvent actionEvent) throws IOException {
        savePlayerName = String.valueOf(playerName.getText());
        logger.log(Level.INFO, "player name: " + String.valueOf(playerName.getText()));

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/gamescene.fxml"));
        logger.log(Level.INFO, "FXML loaded in");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
