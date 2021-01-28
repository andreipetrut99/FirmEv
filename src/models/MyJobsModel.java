package models;

import javafx.beans.property.SimpleStringProperty;

public class MyJobsModel {
    private SimpleStringProperty firstName, lastName, address, phone, hour, date;

    public MyJobsModel(String firstN, String lastN, String addR, String phoN, String houR, String datE) {
        this.firstName = new SimpleStringProperty(firstN);
        this.lastName = new SimpleStringProperty(lastN);
        this.address = new SimpleStringProperty(addR);
        this.phone = new SimpleStringProperty(phoN);
        this.date = new SimpleStringProperty(datE);
        this.hour = new SimpleStringProperty(houR);
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

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
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

    public String getHour() {
        return hour.get();
    }

    public SimpleStringProperty hourProperty() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour.set(hour);
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
}
