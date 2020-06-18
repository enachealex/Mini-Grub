package edu.lwtech.csd299.minigrub;

import java.util.*;

import org.apache.log4j.Logger;

public class UserPojoMemoryDAO implements DAO<UserPojo>{
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

    public int insert(UserPojo user) {
        logger.debug("Inserting " + user + "...");

        if (user.getID() != -1) {
            logger.error("Attempting to add previously added item: " + user);
            return -1;
        }
        
        user = new UserPojo(generateNextItemID(), user.getUserName(), user.getDisplayName(), user.getEmail(), user.getPassword());
        memoryDB.add(user);
        
        logger.debug("Item successfully inserted!");
        return user.getID();
    }

    public void delete(int id) {
        logger.debug("Trying to delete user with ID: " + id);

        UserPojo userFound = null;
        for (UserPojo user : memoryDB) {
            if (user.getID() == id) {
                userFound = user;
                break;
            }
        }
        if (userFound != null)
            memoryDB.remove(userFound);
    }

    public UserPojo getByID(int id) {
        logger.debug("Trying to get user with ID: " + id);
        
        UserPojo userFound = null;
        for (UserPojo user : memoryDB) {
            if (user.getID() == id) {
                userFound = user;
                break;
            }
        }
        return userFound;
    }

    public List<UserPojo> getAll() {
        logger.debug("Getting all users");
        return new ArrayList<>(memoryDB);
    } 
    
    public List<Integer> getAllIDs() {
        logger.debug("Getting User IDs...");

        List<Integer> userIDs = new ArrayList<>();
        for (UserPojo user : memoryDB) {
            userIDs.add(user.getID());
        }
        return userIDs;
    }

    public UserPojo search(String keyword) {
        logger.debug("Trying to get user with email address containing: " + keyword);

        keyword = keyword.toLowerCase();
        List<UserPojo> usersFound = new ArrayList<>();
        for (UserPojo user : memoryDB) {
            if (user.getEmail().toLowerCase().contains(keyword)) {
                usersFound.add(user);
                return user;
            }
        }
        return null;
    }

    public int size() {
        return memoryDB.size();
    }

    public boolean update(UserPojo user) {
        int id = user.getID();
        logger.debug("Updating user (" + id + ") with " + user);

        delete(id);
        memoryDB.add(new UserPojo(id, user));
        memoryDB.sort(Comparator.comparing(UserPojo::getID));
        return true;
    }

    public void disconnect() {
        this.memoryDB = null;
    }

    // =================================================================
    
    public synchronized int generateNextItemID() {
        return nextID++;
    }
}