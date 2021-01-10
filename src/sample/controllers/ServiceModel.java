package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceModel {
    private SimpleStringProperty serviceName;
    private SimpleIntegerProperty time;
    private SimpleStringProperty department;
    private SimpleIntegerProperty price;
    private Button aButton;

    public ServiceModel(String name, int time, String dep, int price) {
        this.serviceName = new SimpleStringProperty(name);
        this.time = new SimpleIntegerProperty(time);
        this.department = new SimpleStringProperty(dep);
        this.price = new SimpleIntegerProperty(price);
        this.aButton = new Button("Click");
        aButton.setOnAction((event -> {
            makeAppoiment(event);
        }));
    }

    public void makeAppoiment(Event event) {
//        System.out.println("Todo: make appoiment");
//        Node node = (Node) event.getSource();
//        Stage stage = (Stage) node.getScene().getWindow();

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../scenes/scheduleAppoimentPage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New appoiment");
            stage.setUserData(this);
            stage.setScene(new Scene(root));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Button getAButton() {
        return aButton;
    }

    public void setAButton(Button aButton) {
        this.aButton = aButton;
    }
}
