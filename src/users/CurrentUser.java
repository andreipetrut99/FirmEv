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
    private boolean isEmployee;

    private int client_id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String adress;
    private Date birthDate;
    private Date registrationDate;

    private int employeeId;
    private String iban;
    private String cnp;
    private String tasks;
    private Date employmentDate;
    private int departmentId;
    private int agencyId;

    private CurrentUser() { }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    private void getInfo() {
        String query = "SELECT C.* FROM Clienti C" +
                " INNER JOIN users U ON U.ClientID = C.ID_client " +
                "WHERE U.ClientId = " + client_id;
        try {
            ResultSet rs = Database.getInstance().runSql(query);
            if (rs.next()) {
                client_id = Integer.parseInt(rs.getString(1));
                lastName = rs.getString(2);
                firstName = rs.getString(3);
                phoneNumber = rs.getString(4);
                adress = rs.getString(5);
                registrationDate = rs.getDate(6);
                birthDate = rs.getDate(7);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (isEmployee) {
            getEmployeeInfo();
        }
    }

    private void getEmployeeInfo() {
        String query = "SELECT C.* FROM Angajati C" +
                " INNER JOIN users U ON U.AngajatID = C.Id_angajat " +
                "WHERE U.AngajatID = " + employeeId;
        try {
            ResultSet rs = Database.getInstance().runSql(query);
            if (rs.next()) {
                cnp = rs.getString(4);
                departmentId = rs.getInt(6);
                employmentDate = rs.getDate(7);
                iban = rs.getString(9);
                agencyId = rs.getInt(10);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void logInUser(String username, String password, int client_id) {
        this.username = username;
        this.password = password;
        this.client_id  = client_id;
        isLoggedIn = true;
        checkEmployee(username);
        getInfo();
    }

    private void checkEmployee(String username) {
        String query = "SELECT AngajatID FROM users WHERE UserId = '" + username + "'";
        try {
            ResultSet rs = Database.getInstance().runSql(query);
            if (rs.next()) {
                if (!(rs.getString(1) == null)) {
                    isEmployee = true;
                    employeeId = rs.getInt(1);
                }
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        isEmployee = false;
    }

    public void logOutUser() {
        isLoggedIn = false;
        username = null;
        password = null;
        isEmployee = false;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
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

    public boolean isEmployee() {
        return isEmployee;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void updateInfo() {
        String query;
        query = "UPDATE clienti SET "
                + "Nume_client = '" + lastName + "', "
                + "Prenume_client = '" + firstName + "', "
                + "Telefon = '" + phoneNumber + "', "
                + "Adresa = '" + adress + "', "
                + "Data_nasterii = '" + birthDate.toString() + "' " +
                "WHERE ID_client = " + client_id;
        try {
            Database.getInstance().runUpdateSql(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // update username and password
        query = "UPDATE users SET "
                + "UserId = '" + username + "', "
                + "UserPassword = " + password + "   " +
                "WHERE ClientID = " + client_id;

        try {
            Database.getInstance().runUpdateSql(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (isEmployee) {
            updateEmployeeInfo();
        }
    }

    public void updateEmployeeInfo() {
        String query;
        query = "UPDATE angajati SET "
                + "Nume = '" + lastName + "', "
                + "Prenume = '" + firstName + "', "
                + "Telefon = '" + phoneNumber + "', "
                + "Data_nasterii = '" + birthDate.toString() + "', "
                + "CNP = '" + cnp + "', "
                + "IBAN_cont = '" + iban + "' " +
                "WHERE ID_angajat = " + employeeId;
        try {
            Database.getInstance().runUpdateSql(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //public void updateEmployee
}
