package edu.lwtech.csd299.minigrub;

public class UserPojo {
    private int id;
    private String userName;
    private String displayName;
    private String email;
    private String password;

    public UserPojo(String userName) {
        this(-1, userName);
    }

    public UserPojo(int id, UserPojo user) {
        this(id, user.userName, user.displayName, user.email, user.password);
    }

    public UserPojo(int id, String email) {
        if (id < -1) throw new IllegalArgumentException("Invalid argument. id < -1.");
        if (email == null) throw new IllegalArgumentException("Invalid argument. Email is null.");
        if (email.equals("")) throw new IllegalArgumentException("Invalid argument. Email is empty.");
        if (!email.contains("@")) throw new IllegalArgumentException("Invalid argument. Email address not valid.");

        this.id = id;
        this.email = email;
        this.userName = email.substring(0, email.indexOf('@'));
    }

    public UserPojo(int id, String userName, String displayName, String email, String password) {
        if (id < -1) throw new IllegalArgumentException("Invalid argument. id < -1.");
        if (email == null) throw new IllegalArgumentException("Invalid argument. Email is null.");
        if (email.equals("")) throw new IllegalArgumentException("Invalid argument. Email is empty.");
        if (!email.contains("@")) throw new IllegalArgumentException("Invalid argument. Email address not valid.");
        if (userName == null || userName == "") userName = email.substring(0, email.indexOf('@'));

        this.id = id;
        this.userName = userName;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
    }

    public int getID() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Hello, " + displayName + "!";
    }
}