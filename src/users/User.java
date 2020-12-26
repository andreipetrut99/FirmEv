package users;

public class User {
    private String username;
    private String password;

    public User(String uName, String pWord) {
        this.username = uName;
        this.password = pWord;
    }

    private void getInfo() {
        // todo get user's info from db.
    }

}

