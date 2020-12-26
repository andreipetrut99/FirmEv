package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class ScreenController extends Application {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public ScreenController() {
        initialize();
    }

    protected void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){
        main.setRoot( screenMap.get(name) );
    }

    protected void initialize() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.main = new Scene(FXMLLoader.load(getClass().getResource("scenes/loginPage.fxml")));
        primaryStage.setTitle("FirmEv");
        primaryStage.setScene(main);
        primaryStage.show();
    }
}
