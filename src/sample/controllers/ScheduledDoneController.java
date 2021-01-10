package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ScheduledDoneController {
    @FXML
    public void close(MouseEvent event) {
        ((Stage)((Node) event.getSource()).getScene().getWindow()).close();
    }
}
