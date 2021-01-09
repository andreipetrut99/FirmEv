package sample.controllers;

import com.mysql.cj.xdevapi.Client;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.security.auth.callback.Callback;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HireController implements Initializable {
    @FXML
    private TableView<ClientModel> tbData;

    @FXML
    public TableColumn<ClientModel, Integer> clientId;

    @FXML
    public TableColumn<ClientModel, String> firstName;

    @FXML
    public TableColumn<ClientModel, String> lastName;
    @FXML
    public TableColumn<ClientModel, String> phone;

    @FXML
    public TableColumn<ClientModel, String> hire;

    private ObservableList<ClientModel> clientsModels;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        hire.setCellValueFactory(new PropertyValueFactory<>("button"));
        //add your data to the table here.

        try {
            setClientsModels();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setClientsModels() throws SQLException {
        //tbClientModel.getItems().add(new ClientModel(1, "asd", "dsa", " aaa"));
        String query = "SELECT * FROM clienti";
        ResultSet rs = Database.getInstance().runSql(query);

        while (rs.next()) {
            int id = rs.getInt(1);
            String firstN = rs.getString(2);
            String lastN = rs.getString(3);
            String phone = rs.getString(4);

            tbData.getItems().add(new ClientModel(id, firstN, lastN, phone));
        }
    }


}
