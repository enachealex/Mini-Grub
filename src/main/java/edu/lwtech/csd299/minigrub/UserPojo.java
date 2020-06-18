package edu.lwtech.csd299.minigrub;

import java.util.*;

public class UserPojo {
    private int id;
    private String userName;
    private String displayName;
    private String email;
    private String password;
    private HashMap<String, Integer> cart = new HashMap<String, Integer>();

    public UserPojo(String email, String password) {
        this(-1, email, password);
    }

    public UserPojo(int id, UserPojo user) {
        this(id, user.email, user.password);
    }

    public UserPojo(int id, String email, String password) {
        if (email == null || email.isEmpty() || !email.contains("@")) throw new IllegalArgumentException("UserPojo: Email cannot be empty or null, and must contain @ symbol.");
        if (password == null || password.isEmpty()) throw new IllegalArgumentException("UserPojo: Password cannot be empty or null.");

        this.id = id;
        this.email = email;
        this.password = password;
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

    public UserPojo(int id, String userName, String displayName, String email, String password, HashMap<String, Integer> cart) {
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
        this.cart = cart;
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
    
    public String getPassword() {
        return password;
    }

    public HashMap<String, Integer> getCart() {
        return cart;
    }

    public void loadCart(String item, int quantity){
        if (item == null || item == "") throw new IllegalArgumentException("Item param missing.");
        if (quantity < 0 || quantity > 999) throw new IllegalArgumentException("Quantity param out of range.");

        cart.put(item, quantity);
    }

    @Override
    public String toString() {
        return "Hello, " + displayName + "!";
    }
}