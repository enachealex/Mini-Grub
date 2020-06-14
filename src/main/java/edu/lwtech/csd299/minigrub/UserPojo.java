package edu.lwtech.csd299.minigrub;

public class UserPojo {
    private int id;
    private String username;
    private String password;

    public UserPojo(String username) {
        this(-1, username);
    }

    public UserPojo(int id, UserPojo user) {
        this(id, user.username, user.password);
    }

    public UserPojo(int id, String username) {
        if (id < -1) throw new IllegalArgumentException("Invalid argument. id < -1.");
        if (username == null) throw new IllegalArgumentException("Invalid argument. username is null.");
        if (username.equals("")) throw new IllegalArgumentException("Invalid argument. username is empty.");

        this.id = id;
        this.username = username;
    }

    public UserPojo(int id, String username, String password) {
        if (id < -1) throw new IllegalArgumentException("Invalid argument. id < -1.");
        if (username == null) throw new IllegalArgumentException("Invalid argument. username is null.");
        if (username.equals("")) throw new IllegalArgumentException("Invalid argument. username is empty.");

        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getID() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    @Override
    public String toString() {
        return "Hello, " + username + "!";
    }
}