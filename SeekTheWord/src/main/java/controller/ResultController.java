package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultController {

    private static Logger logger = Logger.getLogger(ResultController.class.getName());

    @FXML
    private Pane resultScenePane;

    @FXML
    private Pane scorePane;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label allPossibleWordList;

    @FXML
    private Label allPossibleWordList2;

    @FXML
    private Button endGameButton;

    @FXML
    public void initialize() {
        String style = getClass().getResource("/css/Button.css").toExternalForm();
        resultScenePane.getStylesheets().add(style);
        scorePane.getStylesheets().add(style);
        endGameButton.getStylesheets().add(style);
        endGameButton.getStyleClass().add("shiny-orange");
        allPossibleWordList.getStylesheets().add(style);
        allPossibleWordList2.getStylesheets().add(style);

        scoreLabel.setText(String.valueOf(GameController.score));

        logger.log(Level.INFO, "wordsetlist size: " + GameController.wordSetList.size());

        StringBuilder allWords = new StringBuilder();

        int i=0;
        for (String element : GameController.wordSetList) {
            allWords.append(element.toUpperCase() + "\n");
            i++;
        }

        allPossibleWordList.setText(allWords.toString());
    }

    public void closeGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
