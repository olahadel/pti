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
    private Label allPossibleWordList1;

    @FXML
    private Label allPossibleWordList2;

    @FXML
    private Label allPossibleWordList3;

    @FXML
    private Label allPossibleWordList4;

    @FXML
    private Label allPossibleWordList5;

    @FXML
    private Label allPossibleWordList6;

    @FXML
    private Button endGameButton;

    private final int MAXWORDPERLABEL = 12;

    @FXML
    public void initialize() {
        String style = getClass().getResource("/css/Button.css").toExternalForm();
        resultScenePane.getStylesheets().add(style);
        scorePane.getStylesheets().add(style);
        endGameButton.getStylesheets().add(style);
        endGameButton.getStyleClass().add("shiny-orange");
        allPossibleWordList1.getStylesheets().add(style);
        allPossibleWordList2.getStylesheets().add(style);
        allPossibleWordList3.getStylesheets().add(style);
        allPossibleWordList4.getStylesheets().add(style);
        allPossibleWordList5.getStylesheets().add(style);
        allPossibleWordList6.getStylesheets().add(style);

        scoreLabel.setText(String.valueOf(GameController.score));

        logger.log(Level.INFO, "wordsetlist size: " + GameController.wordSetList.size());

        StringBuilder allWords1 = new StringBuilder();
        StringBuilder allWords2 = new StringBuilder();
        StringBuilder allWords3 = new StringBuilder();
        StringBuilder allWords4 = new StringBuilder();
        StringBuilder allWords5 = new StringBuilder();
        StringBuilder allWords6 = new StringBuilder();

        int i=0;
        for (String element : GameController.wordSetList) {
            if (GameController.wordSetList.size()/MAXWORDPERLABEL == 5 || GameController.wordSetList.size()/MAXWORDPERLABEL == 4) {
                if (i/MAXWORDPERLABEL < 1) {
                    allWords1.append(element.toUpperCase() + "\n");
                } else if (i/MAXWORDPERLABEL < 2) {
                    allWords2.append(element.toUpperCase() + "\n");
                } else if (i/MAXWORDPERLABEL < 3) {
                    allWords3.append(element.toUpperCase() + "\n");
                } else if (i/MAXWORDPERLABEL < 4) {
                    allWords4.append(element.toUpperCase() + "\n");
                } else if (i/MAXWORDPERLABEL < 5) {
                    allWords5.append(element.toUpperCase() + "\n");
                } else {
                    allWords6.append(element.toUpperCase() + "\n");
                }
            } else if (GameController.wordSetList.size()/MAXWORDPERLABEL == 3 || GameController.wordSetList.size()/MAXWORDPERLABEL == 2) {
                if (i/MAXWORDPERLABEL < 1) {
                    allWords2.append(element.toUpperCase() + "\n");
                } else if (i/MAXWORDPERLABEL < 2) {
                    allWords3.append(element.toUpperCase() + "\n");
                } else if (i/MAXWORDPERLABEL < 3) {
                    allWords4.append(element.toUpperCase() + "\n");
                } else {
                    allWords5.append(element.toUpperCase() + "\n");
                }
            } else {
                if (i/MAXWORDPERLABEL < 1) {
                    allWords3.append(element.toUpperCase() + "\n");
                } else {
                    allWords4.append(element.toUpperCase() + "\n");
                }
            }

            i++;
        }

        allPossibleWordList1.setText(allWords1.toString());
        allPossibleWordList2.setText(allWords2.toString());
        allPossibleWordList3.setText(allWords3.toString());
        allPossibleWordList4.setText(allWords4.toString());
        allPossibleWordList5.setText(allWords5.toString());
        allPossibleWordList6.setText(allWords6.toString());
    }

    public void closeGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
