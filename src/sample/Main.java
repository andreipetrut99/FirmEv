package sample;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("FirmEv");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        String url = "jdbc:mysql://localhost:3306/firmevdb";
        String username = "root";
        String password = "admin";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String sqlQuery = "SELECT * FROM Angajati";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);

            while (rs.next())
            {
                int id = rs.getInt("Id_angajat");
                String firstName = rs.getString("Nume");
                String lastName = rs.getString("Prenume");
                Date dateCreated = rs.getDate("Data_nasterii");

                // print the results
                System.out.format("%s, %s, %s, %s\n", id, firstName, lastName, dateCreated);
            }
            st.close();

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
