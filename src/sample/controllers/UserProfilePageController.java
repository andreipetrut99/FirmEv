package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import users.CurrentUser;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class UserProfilePageController implements Initializable {
    CurrentUser currentUser = CurrentUser.getInstance();
    @FXML
    Label usernameLabel;
    @FXML
    JFXTextField usernameTextField;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    JFXTextField nameField;
    @FXML
    JFXTextField phoneNumberField;
    @FXML
    JFXTextArea addressTextArea;
    @FXML
    DatePicker birthDate;
    @FXML
    Label registrationDate;
    @FXML
    JFXButton submitButton;
    @FXML
    Label updateText;
    @FXML
    JFXButton logoutButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(currentUser.getUsername() + "!");
        usernameTextField.setText(currentUser.getUsername());
        passwordField.setText(currentUser.getPassword());
        nameField.setText(currentUser.getName());
        phoneNumberField.setText(currentUser.getPhoneNumber());
        addressTextArea.setText(currentUser.getAdress());
        birthDate.setValue(currentUser.getBirthDate().toLocalDate());
        registrationDate.setText(currentUser.getRegistrationDate().toLocalDate().toString());
    }

    @FXML
    public void updateInfo(MouseEvent e) {
        currentUser.setUsername(usernameTextField.getText());
        currentUser.setPassword("MD5('" + passwordField.getText() + "')");
        currentUser.setName(nameField.getText());
        currentUser.setPhoneNumber(phoneNumberField.getText());
        currentUser.setAdress(addressTextArea.getText());
        currentUser.setBirthDate(Date.valueOf(birthDate.getValue()));
        currentUser.updateInfo();
        usernameLabel.setText(currentUser.getUsername() + "!");
        new Thread(() -> {
            updateText.setStyle("-fx-opacity: 1;");
            try {
                Thread.sleep(1200);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            updateText.setStyle("-fx-opacity: 0");
        }).start();
    }

    @FXML
    public void logOut(MouseEvent e) {
        currentUser.logOutUser();
        Parent mainPage = null;
        try {
            mainPage = FXMLLoader.load(getClass().getResource("../scenes/loginPage.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Scene mainScene = new Scene(mainPage);

        // get stage information
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setTitle("FirmEv");

        window.setScene(mainScene);
    }
}
