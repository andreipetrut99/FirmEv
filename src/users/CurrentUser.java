package users;

public class CurrentUser {
    private String username;
    private String password;
    private static CurrentUser instance = null;
    private boolean isLoggedIn;

    private CurrentUser() { }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    private void getInfo() {
        // todo get user's info from db.
    }

    public void logInUser(String username, String password) {
        this.username = username;
        this.password = password;
        isLoggedIn = true;
    }

    public void logOutUser() {
        isLoggedIn = false;
        username = null;
        password = null;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
