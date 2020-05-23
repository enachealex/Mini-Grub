package edu.lwtech.csd299.minigrub;

//import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

//TODO: Update POJO Unit Tests
public class DemoPojoTests {

    DemoPojo DemoPojo;

    @Before
    public void setUp() {
        DemoPojo = new DemoPojo(5, "Test Item");
    }

    @Test
    public void getIdTest() {
        assertEquals(5, DemoPojo.getID());
    }

    @Test
    public void getTitleTest() {
        assertEquals("Test Item", DemoPojo.getName());
    }

    @Test
    public void toStringTest() {
        assertTrue(DemoPojo.toString().startsWith("5:"));
        assertTrue(DemoPojo.toString().contains("Test Item"));
    }

}
