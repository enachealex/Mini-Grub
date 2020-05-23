package edu.lwtech.csd299.minigrub;

//import java.util.*;

//TODO: Create additional POJO classes
public class DemoPojo {
    
    // Encapsulated member variables
    // TODO: Replace these with your actual member variables
    private int id;                 // Database ID (or -1 if it isn't in the database yet)
    private String name;            // Name of the item that the POJO is storing
    
    public DemoPojo(String name) {
        this(-1, name);
    }

    public DemoPojo(int id, DemoPojo demo) {
        this(id, demo.name);
    }
    
    public DemoPojo(int id, String name) {

        if (id < -1) throw new IllegalArgumentException("Invalid argument. id < -1.");
        if (name == null) throw new IllegalArgumentException("Invalid argument. name is null.");
        if (name.equals("")) throw new IllegalArgumentException("Invalid argument. name is empty.");

        this.id = id;
        this.name = name;
    }
    
    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return id + ": " + name;
    }

}
