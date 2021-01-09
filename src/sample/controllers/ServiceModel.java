package sample.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ServiceModel {
    private SimpleStringProperty serviceName;
    private SimpleIntegerProperty time;
    private SimpleStringProperty department;
    private SimpleIntegerProperty price;

    public ServiceModel(String name, int time, String dep, int price) {
        this.serviceName = new SimpleStringProperty(name);
        this.time = new SimpleIntegerProperty(time);
        this.department = new SimpleStringProperty(dep);
        this.price = new SimpleIntegerProperty(price);
    }

    public String getServiceName() {
        return serviceName.get();
    }

    public SimpleStringProperty serviceNameProperty() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName.set(serviceName);
    }

    public int getTime() {
        return time.get();
    }

    public SimpleIntegerProperty timeProperty() {
        return time;
    }

    public void setTime(int time) {
        this.time.set(time);
    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }
}
