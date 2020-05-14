package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Player;
import model.PlayerDAO;
import model.WordSet;
import model.WordSetDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {

    private static Logger logger = Logger.getLogger(GameController.class.getName());

    @FXML
    private Pane gameScenePane;

    @FXML
    private Pane scorePane;

    @FXML
    private Pane endGamePane;

    @FXML
    private Label wordForSeeking;

    @FXML
    private Label presentScore;

    @FXML
    private Label allScore;

    @FXML
    private Button giveUpButton;

    @FXML
    private Button seekButton;

    @FXML
    private Button letterButton0;
    @FXML
    private Button letterButton1;
    @FXML
    private Button letterButton2;
    @FXML
    private Button letterButton3;
    @FXML
    private Button letterButton4;
    @FXML
    private Button letterButton5;
    @FXML
    private Button letterButton6;
    @FXML
    private Button letterButton7;
    @FXML
    private Button letterButton8;

    StringBuilder word = new StringBuilder();
    List<Button> letterButtonList = new ArrayList<>();
    List<Button> clickedButtonList = new ArrayList<>();

    String letterSet = "ABCDEFGHI";
    public static List<String> wordSetList = new ArrayList<>();

    public static int score = 0;
    private long startTime;
    private long endTime;

    private Player player;
    private PlayerDAO playerDAO;
    private WordSetDAO wordSetDAO;

    @FXML
    public void initialize() {

        letterButtonList.add(letterButton0);
        letterButtonList.add(letterButton1);
        letterButtonList.add(letterButton2);
        letterButtonList.add(letterButton3);
        letterButtonList.add(letterButton4);
        letterButtonList.add(letterButton5);
        letterButtonList.add(letterButton6);
        letterButtonList.add(letterButton7);
        letterButtonList.add(letterButton8);

        int i=0;
        for (Button element: letterButtonList) {
            element.setText(String.valueOf(letterSet.charAt(i++)));
        }

        /* SETUP STYLES FOR FXML*/
        String style = getClass().getResource("/css/Button.css").toExternalForm();
        gameScenePane.getStylesheets().add(style);
        scorePane.getStylesheets().add(style);
        endGamePane.getStylesheets().add(style);
        letterButtonList.stream().forEach( x -> {x.getStylesheets().add(style); x.getStyleClass().add("shiny-orange-bordered");});
        seekButton.getStylesheets().add(style);
        seekButton.getStyleClass().add("shiny-orange");
        giveUpButton.getStylesheets().add(style);
        giveUpButton.getStyleClass().add("shiny-orange");

        /* SETUP DEFAULT SCORE */
        //TODO set buttons, set solutionlist
        allScore.setText(String.valueOf(wordSetList.size()));

        /* SETUP TIMER */
        startTime = System.currentTimeMillis();

        /* DATABASE */
        //wordSetDAO = WordSetDAO.getInstance();
        playerDAO = PlayerDAO.getInstance();

        //List<String> allLetterSets = wordSetDAO.findAllLetterSets();
        Random rand = new Random();
        //int randomNumber = rand.nextInt(allLetterSets.size() - 1);

        //wordSetList = wordSetDAO.findAllWords(allLetterSets.get(randomNumber));

        //wordForSeeking.setText(allLetterSets.get(randomNumber));

        /*TRYOUT DB*/
        Player testPlayer = new Player("testuser",30,100);
        WordSet testWordset = new WordSet("abcdefghij", "abc");

        playerDAO.persist(testPlayer);
        //wordSetDAO.persist(testWordset);
    }
    
    public void clickOnLetter(ActionEvent actionEvent) {
        /* SET ONCLICK BUTTON*/
        Button button = (Button) actionEvent.getSource();
        clickedButtonList.add(button);
        button.getStyleClass().clear();
        button.getStyleClass().add("ipad-dark-grey");
        button.setDisable(true);
        int lastCharIndex = button.getId().length() - 1;
        int buttonNumber = Character.getNumericValue(button.getId().charAt(lastCharIndex));

        logger.log(Level.INFO, "Clicked button id: " + button.getId() +
                "\n\t isDisabled: " + button.isDisabled() +
                "\t text: " + button.getText() +
                "\t buttonNumber: " + buttonNumber);

        /* WRITE OUT CURRENT WORD*/
        word.append(button.getText());
        wordForSeeking.setText(String.valueOf(word));

        /* SET REST OF BUTTONS */
        for (Button element: letterButtonList) {
            int elementLastCharIndex = element.getId().length() - 1;
            int elementNumber = Character.getNumericValue(element.getId().charAt(elementLastCharIndex));

            logger.log(Level.INFO, "element number: " + elementNumber + "\t" + element.isDisabled());

            if (buttonNumber == elementNumber || clickedButtonList.contains(element)) {
                continue;
            } else if (
                    buttonNumber/3 == elementNumber/3 && buttonNumber % 3  + 1 == elementNumber % 3 ||      // same row & right column
                    buttonNumber/3 == elementNumber/3 && buttonNumber % 3  - 1 == elementNumber % 3 ||      // same row & left column
                    buttonNumber/3 + 1 == elementNumber/3 && buttonNumber % 3 == elementNumber % 3 ||       // upper row & same column
                    buttonNumber/3 + 1 == elementNumber/3 && buttonNumber % 3 + 1 == elementNumber % 3 ||   // upper row & right column
                    buttonNumber/3 + 1 == elementNumber/3 && buttonNumber % 3 - 1 == elementNumber % 3||    // upper row & left column
                    buttonNumber/3 - 1 == elementNumber/3 && buttonNumber % 3 == elementNumber % 3 ||       // lower row & same column
                    buttonNumber/3 - 1 == elementNumber/3 && buttonNumber % 3 + 1 == elementNumber % 3 ||   // lower row & right column
                    buttonNumber/3 - 1 == elementNumber/3 && buttonNumber % 3 - 1 == elementNumber % 3) {   // lower row & left column
                element.setDisable(false);
            } else {
                element.setDisable(true);
            }
        }
    }

    public void seek(ActionEvent actionEvent) {
        clickedButtonList.clear();

        letterButtonList.stream().forEach( x -> {
            x.setDisable(false);
            x.getStyleClass().clear();
            x.getStyleClass().add("shiny-orange-bordered");});

        if (wordSetList.contains(wordForSeeking.getText())) {
            presentScore.setText(String.valueOf(++score));
        }

        wordForSeeking.setText("");
    }

    public void endGame(ActionEvent actionEvent) throws IOException {
        long endTime = System.currentTimeMillis();
        int gameTime = (int)(endTime - startTime) / 1000;
        player.setPlayerName(StartController.savePlayerName);
        player.setGameTime(gameTime);
        player.setScore(score);

        //playerDAO.persist(player);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/resultscene.fxml"));
        logger.log(Level.INFO, "FXML loaded in");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
