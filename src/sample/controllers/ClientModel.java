package sample.controllers;

import database.Database;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ClientModel {
    private SimpleIntegerProperty clientId;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty phone;
    private Button button;

    public ClientModel(Integer clientId, String firstName, String lastName, String phone) {
        this.clientId = new SimpleIntegerProperty(clientId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleStringProperty(phone);
        this.button = new Button("Hire");

        checkIfEmployed();
        button.setOnAction((event -> {
                hire(this.clientId.get());
        }));
    }

    private void checkIfEmployed() {
        try {
            ResultSet rs = Database.getInstance().runSql("SELECT AngajatID FROM users WHERE ClientID = " + clientId.get());
            if (rs.next()) {
                if (!(rs.getString(1) == null)) {
                   button.setDisable(true);
                   button.setText("Employed");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void hire(int id) {
        try {
            ResultSet rs = Database.getInstance().runSql("SELECT * FROM clienti C WHERE C.ID_client = " + id);
            if (rs.next()) {
                Date birthDate = rs.getDate(7);

                String query = "INSERT INTO Angajati (Nume, Prenume, Telefon, " +
                        "CNP, ID_departament, Data_angajare, Data_nasterii, IBAN_cont)\n " +
                        "VALUES( " +
                        "'" + lastName.get() + "'," +
                        "'" + firstName.get() + "'," +
                        "'" + phone.get() + "'," +
                        "'" + "Complete your CNP" + "'," +
                        "'" + 1 + "'," +
                        "'" + LocalDate.now() + "'," +
                        "'" + birthDate.toString() + "'," +
                        "'" + "Complete your IBAN" + "')";
                Database.getInstance().runUpdateSql(query);
                button.setDisable(true);
                button.setText("Employed");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public int getClientId() {
        return clientId.get();
    }

    public SimpleIntegerProperty clientIdProperty() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId.set(clientId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }



}
