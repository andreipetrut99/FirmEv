package sample.controllers;

import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.AppoimentModel;
import users.CurrentUser;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyAppoimentsController implements Initializable {
    @FXML
    private TableView<AppoimentModel> tbData;

    @FXML
    public TableColumn<AppoimentModel, String> name;

    @FXML
    public TableColumn<AppoimentModel, String> address;

    @FXML
    public TableColumn<AppoimentModel, Integer> appId;

    @FXML
    public TableColumn<AppoimentModel, String> date;

    @FXML
    public TableColumn<AppoimentModel, String> hour;

    @FXML
    public TableColumn<AppoimentModel, String> employee;

    @FXML
    public TableColumn<AppoimentModel, String> deleteRow;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        hour.setCellValueFactory(new PropertyValueFactory<>("hour"));
        employee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        deleteRow.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        appId.setCellValueFactory(new PropertyValueFactory<>("appId"));

        setAppoiments();
    }

    private void setAppoiments() {
        tbData.getItems().clear();
        String query = "SELECT P.*, S.Nume_sarcina, A.Nume, A.Prenume FROM programari P " +
                "JOIN sarcini S ON P.ID_sarcina = S.ID_sarcina " +
                "JOIN angajati A ON P.ID_angajat = A.Id_angajat " +
                "WHERE P.ID_Client = "
                + CurrentUser.getInstance().getClient_id();

        try {
            ResultSet rs = Database.getInstance().runSql(query);
            while (rs.next()) {
                String serviceName = rs.getString("Nume_sarcina");
                String address = rs.getString("Adresa");
                String hour = rs.getString("Ora");
                Date date = rs.getDate("Data");
                int appoimentId = rs.getInt("ID_programare");
                String name = rs.getString("Nume") + " " + rs.getString("Prenume");

                tbData.getItems().add(new AppoimentModel(appoimentId, serviceName, address, date, hour, name));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
