package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ResultController {
    @FXML
    private Pane resultScenePane;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label allPossibleWordsLabel;

    @FXML
    public void initialize() {
        String style = getClass().getResource("/css/Button.css").toExternalForm();
        resultScenePane.getStylesheets().add(style);
        resultScenePane.getStylesheets().add(style);

        scoreLabel.setText(String.valueOf(GameController.score));

        StringBuilder allPossibleWords = new StringBuilder();

        GameController.wordSetList.stream().forEach(x -> {x.concat("\n"); allPossibleWords.append(x);});

        allPossibleWordsLabel.setText(allPossibleWords.toString());
    }
}
