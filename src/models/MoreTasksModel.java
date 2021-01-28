package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MoreTasksModel {
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleIntegerProperty tasksNo;

    public MoreTasksModel(String firstName, String lastName, Integer tasksNo) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.tasksNo = new SimpleIntegerProperty(tasksNo);
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

    public int getTasksNo() {
        return tasksNo.get();
    }

    public SimpleIntegerProperty tasksNoProperty() {
        return tasksNo;
    }

    public void setTasksNo(int tasksNo) {
        this.tasksNo.set(tasksNo);
    }
}
