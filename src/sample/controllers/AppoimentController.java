package sample.controllers;

import com.jfoenix.controls.JFXTextField;
import database.Database;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import users.CurrentUser;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AppoimentController implements Initializable {
    @FXML
    Label name;
    @FXML
    Label department;
    @FXML
    DatePicker date;
    @FXML
    JFXTextField hour;
    @FXML
    TextArea address;
    @FXML
    ChoiceBox<String> employee;
    @FXML
    Label price;

    String serviceName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void init() {
            Stage stage = (Stage) name.getScene().getWindow();
            ServiceModel serviceModel = (ServiceModel) stage.getUserData();

            name.setText(serviceModel.getServiceName());
            serviceName = serviceModel.getServiceName();
            department.setText(serviceModel.getDepartment());

            address.setText(CurrentUser.getInstance().getAdress());
            price.setText((serviceModel.getPrice() * serviceModel.getTime() )+ " RON");

            String query;
            query = "SELECT A.Nume, A.Prenume FROM angajati A \n" +
                    "INNER JOIN sarcina_angajat SA ON SA.ID_Angajat = A.Id_angajat\n" +
                    "WHERE SA.ID_sarcina IN (SELECT ID_sarcina FROM sarcini WHERE " +
                    "Nume_sarcina = '" + serviceModel.getServiceName() + "')";

            try {
                employee.getItems().clear();
                ResultSet rs = Database.getInstance().runSql(query);
                while (rs.next()) {
                    employee.getItems().add(rs.getString(1) + " "
                            + rs.getString(2));
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    @FXML
    public void registerAppoiment(Event e) {
        String query;
        int idDep = 0;
        int idClient = 0;
        String addr = address.getText();
        LocalDate apDate = date.getValue();
        String time = hour.getText();
        int idEmp = 0;

        query = "SELECT ID_departament FROM departamente WHERE Nume_departament = '"
                + department.getText() + "'";

        try {
            ResultSet rs = Database.getInstance().runSql(query);
            if (rs.next())
                idDep = rs.getInt(1);
            idClient = CurrentUser.getInstance().getClient_id();
            String[] name = employee.getValue().split(" ");
            query = "SELECT Id_angajat FROM angajati WHERE Nume = '"
                    + name[0] + "' AND Prenume = '" + name[1] + "'";

            rs = Database.getInstance().runSql(query);

            if (rs.next())
                idEmp = rs.getInt(1);

            query = "SELECT ID_sarcina FROM sarcini WHERE Nume_sarcina = '" + serviceName + "'";

            int id_sarcina = 1;

            rs = Database.getInstance().runSql(query);

            if (rs.next()) {
                id_sarcina = rs.getInt(1);
            }

            query = "INSERT INTO programari(ID_departament, ID_client, Adresa, Data, Ora, ID_angajat, ID_sarcina) " +
                    "VALUES('" + idDep + "'," +
                    "'" + idClient + "'," +
                    "'" + addr + "'," +
                    "'" + apDate.toString() + "'," +
                    "'" + time + "'," +
                    "'" + idEmp + "'," +
                    "'" + id_sarcina + "')";

            Database.getInstance().runUpdateSql(query);

            showDoneWindow(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void showDoneWindow(Event event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../scenes/scheduledDonePage.fxml"));
            Stage stage = ((Stage)((Node) event.getSource()).getScene().getWindow());
            stage.setTitle("Done");
            stage.setScene(new Scene(root));
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(e.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
