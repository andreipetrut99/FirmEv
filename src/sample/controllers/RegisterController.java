package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    JFXTextField usernameTextField;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    JFXPasswordField confirmPasswordField;
    @FXML
    JFXTextField nameField;
    @FXML
    JFXTextField phoneNumberField;
    @FXML
    JFXTextArea addressTextArea;
    @FXML
    DatePicker birthDate;
    @FXML
    JFXButton registerButton;
    @FXML
    Label registerText;
    @FXML
    ImageView checkRegex;
    @FXML
    ImageView checkMatch;
    @FXML
    ImageView checkUser;
    @FXML
    Label takenText;
    @FXML
    Label wrongUsername;
    @FXML
    Label wrongPassword;
    @FXML
    Label wrongMatch;

    @FXML
    private void registerUser() {
        String query = "INSERT INTO Clienti (Nume_client, Telefon, " +
                "Adresa, Data_inregistrare, Data_nasterii)\n " +
                "VALUES( " +
                "'" + nameField.getText() + "'," +
                "'" + phoneNumberField.getText() + "'," +
                "'" + addressTextArea.getText() + "'," +
                "'" + LocalDate.now() + "'," +
                "'" + birthDate.getValue() + "')";

        try {
            if (checkRegex.getOpacity() == 1 && checkMatch.getOpacity() == 1) {
                Database.getInstance().runUpdateSql(query);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int id = 0;
        try {
            ResultSet rs = Database.getInstance().runSql("SELECT MAX(ID_client) FROM Clienti");
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        query = "INSERT INTO users(UserId, UserPassword, ClientId)\n " +
                "VALUES('" + usernameTextField.getText() + "', MD5('" + passwordField +
                "'), '" + id + "')";

        try {
            if (checkConditions()) {
                Database.getInstance().runUpdateSql(query);
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    @FXML
    public void checkPassword() {
        if (passwordField.getText().matches("^(?=.*?[a-z])(?=.*?[0-9]).{8,}$")) {
            checkRegex.setStyle("-fx-opacity: 1");
        } else {
            checkRegex.setStyle("-fx-opacity: 0");
        }
    }

    @FXML
    public void checkPassMatch() {
        if (passwordField.getText().equals(
                confirmPasswordField.getText()
        )) {
            checkMatch.setStyle("-fx-opacity: 1");
        } else {
            checkMatch.setStyle("-fx-opacity: 0");
        }
    }

    @FXML
    public void checkNick() throws SQLException {
        String query = "SELECT U_Id FROM users WHERE UserId = '"
                + usernameTextField.getText() + "'";
        ResultSet rs = null;
        try {
            rs = Database.getInstance().runSql(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (!rs.next() && usernameTextField.getText()!="") {
            checkUser.setStyle("-fx-opacity: 1");
            takenText.setStyle("-fx-opacity: 0");
        } else {
            checkUser.setStyle("-fx-opacity: 0");
            takenText.setStyle("-fx-opacity: 1");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Database.getInstance();
    }

    public boolean checkConditions() {
        if (checkUser.getOpacity() == 1 && checkMatch.getOpacity() == 1
                && checkRegex.getOpacity() == 1) {
            return true;
        }
        if (checkUser.getOpacity() == 0) {
            new Thread(() -> {
                wrongUsername.setStyle("-fx-opacity: 1;");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                wrongUsername.setStyle("-fx-opacity: 0");
            }).start();
        } else if (checkRegex.getOpacity() == 0) {
            new Thread(() -> {
                wrongPassword.setStyle("-fx-opacity: 1;");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                wrongPassword.setStyle("-fx-opacity: 0");
            }).start();
        } else {
            new Thread(() -> {
                wrongMatch.setStyle("-fx-opacity: 1;");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                wrongMatch.setStyle("-fx-opacity: 0");
            }).start();
        }
        return false;
    }
}
