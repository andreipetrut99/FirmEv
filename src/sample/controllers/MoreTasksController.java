package sample.controllers;

import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import users.CurrentUser;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MoreTasksController implements Initializable {
    @FXML
    private TableView<MoreTasksModel> tbData;

    @FXML
    public TableColumn<MoreTasksModel, Integer> tasksNo;

    @FXML
    public TableColumn<MoreTasksModel, String> firstName;

    @FXML
    public TableColumn<MoreTasksModel, String> lastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tasksNo.setCellValueFactory(new PropertyValueFactory<>("tasksNo"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        addToTable();
    }

    private void addToTable() {
        String query = "SELECT A.Nume, A.Prenume, COUNT(SA.ID_sarcina) as numar_sarcini FROM angajati A \n" +
                "JOIN sarcina_angajat SA ON SA.ID_Angajat = A.Id_angajat\n" +
                "group by A.Id_angajat\n" +
                "having numar_sarcini > (SELECT COUNT(SA2.ID_sarcina) FROM sarcina_angajat SA2 WHERE SA2.Id_angajat = "
                + CurrentUser.getInstance().getEmployeeId() + ")";

        try {
            ResultSet rs = Database.getInstance().runSql(query);
            while(rs.next()) {
                String fName, lName;
                int tasksN;
                fName = rs.getString("Prenume");
                lName = rs.getString("Nume");
                tasksN = rs.getInt("numar_sarcini");

                tbData.getItems().add(new MoreTasksModel(fName, lName, tasksN));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
