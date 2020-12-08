package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Database instance = null;

    private Connection conn = null;
    private String dbName = "firmev";

    private String url = "jdbc:mysql://localhost:3306/firmevdb";
    private String username = "root";
    private String password = "admin";

    private Database(){
        connectDb();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void connectDb() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database conected!");
        } catch (SQLException throwables) {
            throw new IllegalStateException("Cannot connect the database!", throwables);
        }
    }

    public ResultSet runSql(String sqlQuery) throws SQLException {
        Statement sta = conn.createStatement();
        return sta.executeQuery(sqlQuery);
    }

    public boolean canConnectUser(String username, String password) throws SQLException {
        String checkUserQuery = "SELECT UserId FROM users WHERE UserPassword=MD5('"  + password + "')";
        ResultSet rs = runSql(checkUserQuery);
        if (rs.next()) {
            rs.next();
            String dbPass = rs.getString(1);
            return username.equals(dbPass);
        } else {
            return false;
        }
    }
}
