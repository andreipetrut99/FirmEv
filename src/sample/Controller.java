package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label label;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXTextField usernameLabel;
    @FXML
    private JFXPasswordField passwordLabel;
    @FXML
    private JFXButton registerButton;
    @FXML
    private Hyperlink forgotPassLabel;

    @FXML
    private void handleExit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void pressedButton(MouseEvent event) {
        System.out.println("ai apasat butonu");
    }

    @FXML
    private void logIn(MouseEvent event) throws SQLException {
        System.out.println(Database.getInstance().canConnectUser(
                usernameLabel.getText(), passwordLabel.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
