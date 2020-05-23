package edu.lwtech.csd299.minigrub;

import java.util.*;

import org.apache.log4j.Logger;

//TODO: Create additional DAO classes for additional POJO classes
//TODO: Refactor DemoPojo to a real POJO class name
public class DemoPojoMemoryDAO implements DAO<DemoPojo> {
    
    private static final Logger logger = Logger.getLogger(DemoPojoMemoryDAO.class.getName());
    
    private int nextID;
    private List<DemoPojo> memoryDB;

    public DemoPojoMemoryDAO() {
        this.nextID = 1000;
        this.memoryDB = new ArrayList<>();
        // addSampleData();
    }

    public boolean init() {
        return true;
    }

    public int insert(DemoPojo item) {
        logger.debug("Inserting " + item + "...");

        if (item.getID() != -1) {
            logger.error("Attempting to add previously added item: " + item);
            return -1;
        }
        
        item = new DemoPojo(generateNextItemID(), item.getName());
        memoryDB.add(item);
        
        logger.debug("Item successfully inserted!");
        return item.getID();
    }
    
    public void delete(int id) {
        logger.debug("Trying to delete item with ID: " + id);

        DemoPojo itemFound = null;
        for (DemoPojo item : memoryDB) {
            if (item.getID() == id) {
                itemFound = item;
                break;
            }
        }
        if (itemFound != null)
            memoryDB.remove(itemFound);
    }

    public DemoPojo getByID(int id) {
        logger.debug("Trying to get item with ID: " + id);
        
        DemoPojo itemFound = null;
        for (DemoPojo item : memoryDB) {
            if (item.getID() == id) {
                itemFound = item;
                break;
            }
        }
        return itemFound;
    }
    
    public DemoPojo getByIndex(int index) {
        // Note: indexes are zero-based
        logger.debug("Trying to get item with index: " + index);

        if (index < 0 || index > memoryDB.size())
            return null;

        return memoryDB.get(index);
    }
    
    public List<DemoPojo> getAll() {
        logger.debug("Getting all items");
        return new ArrayList<>(memoryDB);
    }    
    
    public List<Integer> getAllIDs() {
        logger.debug("Getting Item IDs...");

        List<Integer> itemIDs = new ArrayList<>();
        for (DemoPojo item : memoryDB) {
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
