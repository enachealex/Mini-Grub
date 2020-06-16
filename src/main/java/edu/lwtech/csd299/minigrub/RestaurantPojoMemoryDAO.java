package edu.lwtech.csd299.minigrub;

import java.util.*;

import org.apache.log4j.Logger;

//TODO: Create additional DAO classes for additional POJO classes

public class RestaurantPojoMemoryDAO implements DAO<RestaurantPojo> {
    
    private static final Logger logger = Logger.getLogger(RestaurantPojoMemoryDAO.class.getName());
    
    private int nextID;
    private List<RestaurantPojo> memoryDB;

    public RestaurantPojoMemoryDAO() {
        this.nextID = 1000;
        this.memoryDB = new ArrayList<>();
        // addSampleData();
    }

    public boolean init() {
        return true;
    }

    public int insert(RestaurantPojo item) {
        logger.debug("Inserting " + item + "...");

        if (item.getID() != -1) {
            logger.error("Attempting to add previously added item: " + item);
            return -1;
        }
        
        item = new RestaurantPojo(generateNextItemID(), item.getName(), item.getDescription(), item.getMenu());
        memoryDB.add(item);
        
        logger.debug("Item successfully inserted!");
        return item.getID();
    }
    
    public void delete(int id) {
        logger.debug("Trying to delete item with ID: " + id);

        RestaurantPojo itemFound = null;
        for (RestaurantPojo item : memoryDB) {
            if (item.getID() == id) {
                itemFound = item;
                break;
            }
        }
        if (itemFound != null)
            memoryDB.remove(itemFound);
    }

    public RestaurantPojo getByID(int id) {
        logger.debug("Trying to get item with ID: " + id);
        
        RestaurantPojo itemFound = null;
        for (RestaurantPojo item : memoryDB) {
            if (item.getID() == id) {
                itemFound = item;
                break;
            }
        }
        return itemFound;
    }
    
    public List<RestaurantPojo> getAll() {
        logger.debug("Getting all items");
        return new ArrayList<>(memoryDB);
    }    
    
    public List<Integer> getAllIDs() {
        logger.debug("Getting Item IDs...");

        List<Integer> itemIDs = new ArrayList<>();
        for (RestaurantPojo item : memoryDB) {
            itemIDs.add(item.getID());
        }
        return itemIDs;
    }

    public void disconnect() {
        this.memoryDB = null;
    }

    // =================================================================
    
    public synchronized int generateNextItemID() {
        return nextID++;
    }
}
