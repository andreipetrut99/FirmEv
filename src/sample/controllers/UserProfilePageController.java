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
    JFXTextField firstNameField;
    @FXML
    JFXTextField lastNameField;
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
    @FXML
    JFXTextField cnpTextField;
    @FXML
    JFXTextField ibanTextField;
    @FXML
    Label employeeIdField;
    @FXML
    Label employmentDateField;
    @FXML
    JFXTextArea tasksField;
    @FXML
    JFXButton submitButton1;
    @FXML
    Label updateText2;
    @FXML
    JFXButton allServicesButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(currentUser.getUsername() + "!");
        usernameTextField.setText(currentUser.getUsername());
        passwordField.setText(currentUser.getPassword());
        firstNameField.setText(currentUser.getFirstName());
        lastNameField.setText(currentUser.getLastName());
        phoneNumberField.setText(currentUser.getPhoneNumber());
        addressTextArea.setText(currentUser.getAdress());
        birthDate.setValue(currentUser.getBirthDate().toLocalDate());
        registrationDate.setText(currentUser.getRegistrationDate().toLocalDate().toString());

        if (CurrentUser.getInstance().isEmployee()) {
            ibanTextField.setText(currentUser.getIban());
            cnpTextField.setText(currentUser.getCnp());
            employeeIdField.setText(String.valueOf(currentUser.getEmployeeId()));
            String tasks = CurrentUser.getInstance().getTasks();
            tasksField.setText(tasks);
            employmentDateField.setText(currentUser.getEmploymentDate().toString());
            tasksField.setDisable(true);
            tasksField.setStyle("-fx-opacity: 1");
        }
    }

    @FXML
    public void updateInfo(MouseEvent e) {
        currentUser.setUsername(usernameTextField.getText());
        currentUser.setPassword("MD5('" + passwordField.getText() + "')");
        currentUser.setFirstName(firstNameField.getText());
        currentUser.setLastName(lastNameField.getText());
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
    public void updateEmployeeInfo(MouseEvent e) {
        updateInfo(e);
        currentUser.setCnp(cnpTextField.getText());
        currentUser.setIban(ibanTextField.getText());
        currentUser.updateInfo();

        new Thread(() -> {
            updateText2.setStyle("-fx-opacity: 1;");
            try {
                Thread.sleep(1200);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            updateText2.setStyle("-fx-opacity: 0");
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

    @FXML
    public void hirePerson() {
        showStage("../scenes/hirePage.fxml", "Hire page");
    }

    @FXML
    public void showServices() {
        showStage("../scenes/servicesPage.fxml", "Our services");
    }

    private void showStage(String path, String title) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(e.getSource())).getScene().getWindow().hide();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
