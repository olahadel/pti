package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(MyApplication.class.getResource("/fxml/startscene.fxml"));
        stage.setTitle("Seek The Word");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
