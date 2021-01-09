package sample.controllers;

import database.Database;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ClientModel {
    private SimpleIntegerProperty clientId;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty phone;
    private SimpleStringProperty username;
    private Button hireButton;
    private Button removeButton;

    public ClientModel(Integer clientId, String firstName, String lastName, String phone, String username) {
        this.username = new SimpleStringProperty(username);
        this.clientId = new SimpleIntegerProperty(clientId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleStringProperty(phone);
        this.hireButton = new Button("Hire");
        this.removeButton = new Button("Remove");

        checkIfEmployed();
        hireButton.setOnAction((event -> {
                hire(this.clientId.get());
        }));

        removeButton.setOnAction((event -> removeEntry(this.clientId.get())));
    }

    private void checkIfEmployed() {
        try {
            ResultSet rs = Database.getInstance().runSql("SELECT AngajatID FROM users WHERE ClientID = " + clientId.get());
            if (rs.next()) {
                if (!(rs.getString(1) == null)) {
                   hireButton.setDisable(true);
                   hireButton.setText("Employed");
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
                hireButton.setDisable(true);
                hireButton.setText("Employed");

                int lastId = 0;
                rs = Database.getInstance().runSql("SELECT MAX(Id_angajat) FROM angajati");
                if (rs.next()) {
                    lastId = rs.getInt(1);
                }
                query = "UPDATE users SET AngajatID = " + lastId + " WHERE ClientID = " + clientId.get();
                Database.getInstance().runUpdateSql(query);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeEntry(int clientId) {
        String query;
        ResultSet rs = null;
        try {
            rs = Database.getInstance().runSql("SELECT AngajatID FROM users WHERE ClientID = " + clientId);
            if (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    query = "DELETE FROM angajati WHERE ID_angajat = " + rs.getInt(1);
                    Database.getInstance().runUpdateSql(query);
                }
            }
            query = "DELETE FROM users WHERE ClientID = " + clientId;
            Database.getInstance().runUpdateSql(query);

            query = "DELETE FROM clienti WHERE ID_client = " + clientId;
            Database.getInstance().runUpdateSql(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Button getHireButton() {
        return hireButton;
    }

    public void setHireButton(Button hireButton) {
        this.hireButton = hireButton;
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


    public Button getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(Button removeButton) {
        this.removeButton = removeButton;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }
}
