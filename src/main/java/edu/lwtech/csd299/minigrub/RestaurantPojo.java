package edu.lwtech.csd299.minigrub;

import java.util.*;

//TODO: Create additional POJO classes
public class RestaurantPojo {
    
    // Encapsulated member variables
    // TODO: Replace these with your actual member variables
    private int id;                 // Database ID (or -1 if it isn't in the database yet)
    private String name;            // Name of the restaurant that pojo is storing
    private String description;     // Description of the restaurant
    private HashMap<String, Float> menu;
    
    public RestaurantPojo(String name) {
        this(-1, name);
    }

    public RestaurantPojo(int id, RestaurantPojo restaurant) {
        this(id, restaurant.name);
    }

    public RestaurantPojo(RestaurantPojo restaurant, HashMap<String, Float> menu) {
        this(restaurant.id, restaurant.name, restaurant.description, menu);
    }

    public RestaurantPojo(int id, String name, String description, HashMap<String, Float> menu) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.menu = menu;
    }
    
    public RestaurantPojo(int id, String name) {
        if (id < -1) throw new IllegalArgumentException("Invalid argument. id < -1.");
        if (name == null) throw new IllegalArgumentException("Invalid argument. name is null.");
        if (name.equals("")) throw new IllegalArgumentException("Invalid argument. name is empty.");

        this.id = id;
        this.name = name;
    }

    public RestaurantPojo(int id, String name, String description) {
        if (id < -1) throw new IllegalArgumentException("Invalid argument. id < -1.");
        if (name == null) throw new IllegalArgumentException("Invalid argument. name is null.");
        if (name.equals("")) throw new IllegalArgumentException("Invalid argument. name is empty.");

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void insertMenuItem(String item, Float price) {
        this.menu.put(item, price);
    }

    public HashMap<String, Float> getMenu() {
        return menu;
    }
    
    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }

}
