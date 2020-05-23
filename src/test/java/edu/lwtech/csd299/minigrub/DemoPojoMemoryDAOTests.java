package edu.lwtech.csd299.minigrub;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

//TODO: Update DAL Unit Tests
public class DemoPojoMemoryDAOTests {

    DAO<DemoPojo> memoryDAO;
    DemoPojo thirdItem;

    @Before
    public void setUp() {
        memoryDAO = new DemoPojoMemoryDAO();
        memoryDAO.insert(new DemoPojo("Test Item 1"));
        memoryDAO.insert(new DemoPojo("Test Item 2"));
        thirdItem = new DemoPojo("Test Item 3");
    }

    @Test
    public void insertTest() {
        memoryDAO.insert(thirdItem);
        assertEquals(3, memoryDAO.getAllIDs().size());
    }
    
    @Test
    public void getByIDTest() {
        DemoPojo item = memoryDAO.getByID(1000);
        assertEquals(1000, item.getID());
        item = memoryDAO.getByID(1001);
        assertEquals(1001, item.getID());
    }
    
    @Test
    public void getByIndexTest() {
        DemoPojo item = memoryDAO.getByIndex(0);
        assertEquals(1000, item.getID());
        item = memoryDAO.getByIndex(1);
        assertEquals(1001, item.getID());
    }
    
    @Test
    public void getAllDemoPojosTest() {
        List<DemoPojo> allItems = new ArrayList<>();
        allItems = memoryDAO.getAll();
        assertEquals(2, allItems.size());
    }    
    
}
