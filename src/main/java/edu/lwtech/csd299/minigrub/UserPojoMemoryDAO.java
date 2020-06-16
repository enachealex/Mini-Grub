package edu.lwtech.csd299.minigrub;

import java.util.*;

import org.apache.log4j.Logger;

public class UserPojoMemoryDAO {
    private static final Logger logger = Logger.getLogger(RestaurantPojoMemoryDAO.class.getName());

    private int nextID;
    private List<UserPojo> memoryDB;

    public UserPojoMemoryDAO() {
        this.nextID = 1000;
        this.memoryDB = new ArrayList<>();
        // addSampleData();
    }

    public boolean init() {
        return true;
    }
}