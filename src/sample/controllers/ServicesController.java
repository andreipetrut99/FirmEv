package sample.controllers;

import com.jfoenix.controls.JFXTextField;
import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ServicesController implements Initializable {
    @FXML
    private TableView<ServiceModel> tbData;

    @FXML
    public TableColumn<ServiceModel, String> serviceName;

    @FXML
    public TableColumn<ServiceModel, Integer> time;

    @FXML
    public TableColumn<ServiceModel, Integer> department;

    @FXML
    public TableColumn<ServiceModel, Integer> price;

    @FXML
    public JFXTextField searchBar;

    @FXML
    public ChoiceBox<String> searchCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        setServices("");
        addChoises();
    }

    @FXML
    private void search() {
        String department = searchCategory.getValue();
        String subquery =  (department.equals("all")) ? "" : " AND D.ID_departament IN (SELECT ID_departament " +
                "FROM departamente WHERE Nume_departament = '" + department +"')";
        String searchDep = "\nWHERE S.Nume_sarcina LIKE '%" + searchBar.getText() + "%'";

        setServices(searchDep + subquery);
    }

    private void addChoises() {
        String query = "SELECT D.Nume_departament FROM departamente D";
        try {
            ResultSet rs = Database.getInstance().runSql(query);
            searchCategory.getItems().add("all");
            searchCategory.setValue("all");
            while (rs.next()) {
                searchCategory.getItems().add(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setServices(String condition) {
        tbData.getItems().clear();
        String query = "SELECT S.*, D.Nume_departament FROM departamente D \n" +
                "INNER JOIN sarcini S ON S.ID_departament = D.ID_departament\n" + condition;
        try {
            ResultSet rs = Database.getInstance().runSql(query);
            while (rs.next()) {
                String name = rs.getString(2);
                Integer time = rs.getInt(3);
                Integer price = rs.getInt(5);
                String dep = rs.getString(6);

                tbData.getItems().add(new ServiceModel(name, time, dep, price));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
