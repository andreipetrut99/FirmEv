package models;

import database.Database;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

import java.sql.Date;
import java.sql.SQLException;

public class AppoimentModel {
    private SimpleIntegerProperty appId;
    private SimpleStringProperty name;
    private SimpleStringProperty address;
    private SimpleStringProperty date;
    private SimpleStringProperty hour;
    private SimpleStringProperty employee;
    private Button deleteButton;

    public AppoimentModel(int appId, String name, String address,
                          Date date, String hour, String employee) {
        this.appId = new SimpleIntegerProperty(appId);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.date = new SimpleStringProperty(date.toString());
        this.hour = new SimpleStringProperty(hour);
        this.employee = new SimpleStringProperty(employee);
        deleteButton = new Button("Delete");

        deleteButton.setOnAction((event -> deleteAppoiment(appId)));
    }

    private void deleteAppoiment(int id) {
        try {
            Database.getInstance().runUpdateSql("DELETE FROM programari WHERE ID_programare = " + id);
            deleteButton.setDisable(true);
            deleteButton.setText("deleted");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getAppId() {
        return appId.get();
    }

    public SimpleIntegerProperty appIdProperty() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId.set(appId);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getHour() {
        return hour.get();
    }

    public SimpleStringProperty hourProperty() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour.set(hour);
    }

    public String getEmployee() {
        return employee.get();
    }

    public SimpleStringProperty employeeProperty() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee.set(employee);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
