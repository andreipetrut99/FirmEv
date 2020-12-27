package users;

import database.Database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrentUser {
    private String username;
    private String password;
    private static CurrentUser instance = null;
    private boolean isLoggedIn;

    private int user_id;
    private String name;
    private String phoneNumber;
    private String adress;
    private Date birthDate;
    private Date registrationDate;

    private CurrentUser() { }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    private void getInfo() {
        // todo get user's info from db.
        String query = "SELECT C.* FROM Clienti C" +
                " INNER JOIN users U ON U.ClientID = C.ID_client";
        try {
            ResultSet rs = Database.getInstance().runSql(query);
            if (rs.next()) {
                user_id = Integer.parseInt(rs.getString(1));
                name = rs.getString(2);
                phoneNumber = rs.getString(3);
                adress = rs.getString(4);
                registrationDate = rs.getDate(5);
                birthDate = rs.getDate(6);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void logInUser(String username, String password) {
        this.username = username;
        this.password = password;
        isLoggedIn = true;
        getInfo();
    }

    public void logOutUser() {
        isLoggedIn = false;
        username = null;
        password = null;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void updateInfo() {
        String query;
        query = "UPDATE clienti SET "
                + "Nume_client = '" + name + "', "
                + "Telefon = '" + phoneNumber + "', "
                + "Adresa = '" + adress + "', "
                + "Data_nasterii = '" + birthDate.toString() + "' " +
                "WHERE ID_client = " + user_id;
        try {
            Database.getInstance().runUpdateSql(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // update username and password
        query = "UPDATE users SET "
                + "UserId = '" + username + "', "
                + "UserPassword = " + password + " " +
                "WHERE ClientID = " + user_id;

        try {
            Database.getInstance().runUpdateSql(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
