package sample.controllers;

import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.MyJobsModel;
import users.CurrentUser;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MyJobsController implements Initializable {
    @FXML
    ChoiceBox<String> filterDate;

    @FXML
    private TableView<MyJobsModel> tbData;

    @FXML
    public TableColumn<MyJobsModel, String> firstName;

    @FXML
    public TableColumn<MyJobsModel, String> lastName;

    @FXML
    public TableColumn<MyJobsModel, String> phone;

    @FXML
    public TableColumn<MyJobsModel, String> date;

    @FXML
    public TableColumn<MyJobsModel, String> hour;

    @FXML
    public TableColumn<MyJobsModel, String> address;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        hour.setCellValueFactory(new PropertyValueFactory<>("hour"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        addChoises();
        addToTable();
    }

    private void addToTable() {
        tbData.getItems().clear();

        String condition = "";
        String value = filterDate.getValue();
        Date date1 = Date.valueOf(LocalDate.now());
        if (value.equals("today")) {
            date1 = Date.valueOf(LocalDate.now());
        } else if (value.equals("tomorrow")) {
            date1 = Date.valueOf(LocalDate.now().plusDays(1));
        } else if (value.equals("the day after tomorrow")) {
            date1 = Date.valueOf(LocalDate.now().plusDays(2));
        }

        if (!value.equals("any date")) {
            condition = " AND P.Data = '" + date1.toString() + "'";
        }

        String query = " SELECT C.Nume_client, C.Prenume_client, C.Telefon, P.Adresa, P.Data, P.Ora FROM programari P \n" +
                " JOIN clienti C ON C.ID_client = P.ID_client\n" +
                " WHERE P.ID_angajat = " + CurrentUser.getInstance().getEmployeeId() + condition;

        try {
            ResultSet rs = Database.getInstance().runSql(query);

            while (rs.next()) {
                String fName, lName, phone, address, date, hour;
                fName = rs.getString("Prenume_client");
                lName = rs.getString("Nume_client");
                phone = rs.getString("Telefon");
                address = rs.getString("Adresa");
                date = rs.getDate("Data").toString();
                hour = rs.getString("Ora");

                tbData.getItems().add(new MyJobsModel(fName, lName, address, phone, hour, date));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addChoises() {
        filterDate.getItems().add("any date");
        filterDate.getItems().add("today");
        filterDate.getItems().add("tomorrow");
        filterDate.getItems().add("the day after tomorrow");
        filterDate.setValue("any date");
    }

    @FXML
    public void refresh() {
        addToTable();
    }
}
