package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegisterController {
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
            Database.getInstance().runUpdateSql(query);
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
            Database.getInstance().runUpdateSql(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
    }
}
