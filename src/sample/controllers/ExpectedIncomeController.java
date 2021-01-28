package sample.controllers;

import com.jfoenix.controls.JFXButton;
import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ExpectedIncomeController implements Initializable {
    @FXML
    JFXButton closeButton;

    @FXML
    Label amountLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amountLabel.setText(getAmount() + " RON");
    }

    public int getAmount() {
        String query = "SELECT SUM(SA.Pret_ora * SA.Durata * (SELECT COUNT(P.ID_programare) " +
                "FROM programari P WHERE P.ID_sarcina = SA.ID_sarcina)) FROM sarcini SA";

        try {
            ResultSet rs = Database.getInstance().runSql(query);
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    @FXML
    public void close(MouseEvent event) {
        ((Stage)((Node) event.getSource()).getScene().getWindow()).close();
    }
}
