package sample.controllers;

import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.NoAppoimentModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NoAppoimentsController implements Initializable {
    @FXML
    ChoiceBox<Integer> top;

    @FXML
    ChoiceBox<String> group;

    @FXML
    private TableView<NoAppoimentModel> tbData;

    @FXML
    public TableColumn<NoAppoimentModel, Integer> empId;

    @FXML
    public TableColumn<NoAppoimentModel, String> firstName;

    @FXML
    public TableColumn<NoAppoimentModel, String> lastName;

    @FXML
    public TableColumn<NoAppoimentModel, String> department;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        empId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));

        addChoises();
        addToTable();
    }

    @FXML
    public void addToTable() {
        tbData.getItems().clear();
        String orderby = "";
        if (group.getValue().equals("first name")) {
            orderby = "A.Prenume \n";
        } else if (group.getValue().equals("last name")) {
            orderby = "A.Nume \n";
        } else {
            orderby = "D.Nume_departament \n";
        }

        String query = "SELECT A.ID_angajat, A.Nume, A.Prenume, D.Nume_departament FROM angajati A\n" +
                "JOIN departamente D on D.ID_departament = A.ID_departament\n" +
                "WHERE A.Id_angajat NOT IN (SELECT DISTINCT A2.Id_angajat FROM angajati A2, programari P WHERE P.ID_angajat = A2.Id_angajat)\n" +
                "ORDER BY " + orderby +
                "LIMIT " + top.getValue();

        try {
            ResultSet rs = Database.getInstance().runSql(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String depName = rs.getString(4);

                tbData.getItems().add(new NoAppoimentModel(id, lastName, firstName, depName));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void addChoises() {
        group.getItems().clear();
        group.getItems().add("first name");
        group.getItems().add("last name");
        group.getItems().add("depart. name");
        group.setValue("first name");

        top.getItems().clear();
        for (int i = 1; i < 6; i++) {
            top.getItems().add(i);
        }
        top.setValue(3);
    }

    @FXML
    public void refresh() {
        addToTable();
    }
}
