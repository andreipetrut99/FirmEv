package sample.controllers;

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

public class BestEmployeeController implements Initializable {
    @FXML
    Label name;

    @FXML
    Label department;

    @FXML
    Label appoiments;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showBestEmployee();
    }

    private void showBestEmployee() {
        String query = "SELECT A.Nume, A.Prenume, D.Nume_departament, count(P.ID_programare) AS Numar_programari FROM angajati A\n" +
                "JOIN departamente D ON D.ID_departament = A.ID_departament\n" +
                "JOIN programari P ON P.Id_angajat = A.Id_angajat\n" +
                "GROUP BY A.Id_angajat\n" +
                "ORDER BY Numar_programari DESC\n" +
                "LIMIT 1";

        try {
            ResultSet rs = Database.getInstance().runSql(query);
            if (rs.next()) {
                name.setText(rs.getString(1 ) + " " + rs.getString(2));
                appoiments.setText(rs.getInt(4) + " appoiments");
                department.setText(rs.getString(3) + " department");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void close(MouseEvent e) {
        ((Stage)((Node) e.getSource()).getScene().getWindow()).close();
    }
}
