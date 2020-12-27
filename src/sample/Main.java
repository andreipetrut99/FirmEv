package sample;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import users.CurrentUser;

public class Main extends Application {

    public static void main(String[] args) {
        CurrentUser currentUser = CurrentUser.getInstance();
        Application.launch(args);
        Database db = Database.getInstance();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("scenes/loginPage.fxml")));
        primaryStage.setTitle("FirmEv");
        primaryStage.setScene(main);
        primaryStage.show();
    }
}
