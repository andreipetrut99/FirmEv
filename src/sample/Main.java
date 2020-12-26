package sample;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import users.CurrentUser;

public class Main {

    public static void main(String[] args) {
        CurrentUser currentUser = CurrentUser.getInstance();
        ScreenController screenController = new ScreenController();
        Application.launch(ScreenController.class, args);
        Database db = Database.getInstance();
    }
}
