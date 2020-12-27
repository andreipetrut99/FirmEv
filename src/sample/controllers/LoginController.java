package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import users.CurrentUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
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
    private Label worngPassLabel;

    @FXML
    private void handleExit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void pressedButton(MouseEvent event) {
        System.out.println("ai apasat butonu");
    }

    @FXML
    private void logIn(ActionEvent event) throws SQLException, IOException {
        String username = usernameLabel.getText();
        String password = passwordLabel.getText();
        if (Database.getInstance().canConnectUser(username, password)) {
            CurrentUser.getInstance().logInUser(username, password);

            Parent mainPage = FXMLLoader.load(getClass().getResource("../scenes/userProfilePage.fxml"));
            Scene mainScene = new Scene(mainPage);

            // get stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("My data");

            window.setScene(mainScene);
        } else {
            new Thread(() -> {
                worngPassLabel.setStyle("-fx-opacity: 1;");
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                worngPassLabel.setStyle("-fx-opacity: 0");
            }).start();
        }
    }

    @FXML
    private void onEnterLogin(KeyEvent e) throws SQLException {
        if (e.getCode() == KeyCode.ENTER) {
            loginButton.fire();
        }
    }

    @FXML
    private void register(MouseEvent e) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../scenes/registerPage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Register new user");
            stage.setScene(new Scene(root));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(e.getSource())).getScene().getWindow().hide();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
