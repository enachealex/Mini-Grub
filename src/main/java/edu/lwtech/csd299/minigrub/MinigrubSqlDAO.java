package edu.lwtech.csd299.minigrub;

import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

public class MinigrubSqlDAO {
    private static final Logger logger = Logger.getLogger(MinigrubSqlDAO.class.getName());

    private Connection conn = null;

    public MinigrubSqlDAO() {
        this.conn = null;                                   // conn must be created during init()
    }

    public boolean init() {
        logger.info("Connecting to the database...");

        String jdbcDriver = "org.mariadb.jdbc.Driver";      // The MariaDB driver works better than the MySQL driver
        String url = "jdbc:mariadb://localhost:3306/topten?useSSL=false&allowPublicKeyRetrieval=true";

        conn = SQLUtils.connect(url, "minigrub", "lwtech2000", jdbcDriver);       //TODO: Remove DB credentials from the source code!
        if (conn == null) {
            logger.error("Unable to connect to SQL Database with URL: " + url);
            return false;
        }
        logger.info("...connected!");

        return true;
    }

    public int insert(RestaurantPojo restaurant) {
        logger.debug("Inserting " + restaurant + "...");

        if (restaurant.getID() != -1) {
            logger.error("Attempting to add previously added Restaurant: " + restaurant);
            return -1;
        }

        String query = "INSERT INTO Restaurant";
        query += " (description)";
        query += " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String name = restaurant.getName();
        String description = restaurant.getDescription();
        //List<String> items = restaurant.getItems();
        //String isPublished = restaurant.isPublished() ? "Y" : "N";
        //String ownerID = "" + restaurant.getOwnerID();
        //String numViews = "" + restaurant.getNumViews();
        //String numLikes = "" + restaurant.getNumLikes();

        int restaurantID = SQLUtils.executeSQLInsert(conn, query, "restaurantID", description);    
        
        logger.debug("TopTenList successfully inserted with listID = " + restaurantID);
        return restaurantID;
    }

    public void delete(int restaurantID) {
        logger.debug("Trying to delete Restaurant with ID: " + restaurantID);

        String query = "DELETE FROM Restaurants WHERE restaurantID=" + restaurantID;
        SQLUtils.executeSQL(conn, query);
    }
    
    public RestaurantPojoMemoryDAO getByID(int restaurantID) {
        logger.debug("Trying to get Restaurant with ID: " + restaurantID);
        
        String query = "SELECT restaurantID, description,";
        query += " item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, isPublished, ownerID, numViews, numLikes";
        query += " FROM TopTenLists WHERE restaurantID=" + restaurantID;

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);
        
        if (rows != null) {
            logger.debug("Found list!");
        } else {
            logger.debug("Did not find list.");
            return null;
        }
        
        SQLRow row = rows.get(0);
        RestaurantPojoMemoryDAO restaurant = convertRowToList(row);
        return restaurant;
    }
    
    public List<RestaurantPojoMemoryDAO> getAll() {
        logger.debug("Getting all TopTenLists...");
        
        String query = "SELECT listID, description,";
        query += " item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, isPublished, ownerID, numViews, numLikes";
        query += " FROM TopTenLists ORDER BY listID";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);
        
        if (rows == null) {
            logger.debug("No lists found!");
            return null;
        }
       
        List<TopTenList> lists = new ArrayList<>();
        for (SQLRow row : rows) {
            TopTenList toptenlist = convertRowToList(row);
            lists.add(toptenlist);
        }
        return lists;
    }
    
    public List<Integer> getAllIDs() {
        logger.debug("Getting List IDs...");

        String query = "SELECT listID FROM TopTenLists ORDER BY listID";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);
        
        if (rows == null) {
            logger.debug("No lists found!");
            return null;
        }
        
        List<Integer> listIDs = new ArrayList<>();
        for (SQLRow row : rows) {
            String value = row.getItem("listID");
            int i = Integer.parseInt(value);
            listIDs.add(i);
        }
        return listIDs;
    }

    @Override
    public int size() {
        logger.debug("Getting the number of rows...");

        String query = "SELECT count(*) FROM TopTenLists";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);
        if (rows == null) {
            logger.error("No lists found!");
            return 0;
        }

        String value = rows.get(0).getItem();
        return Integer.parseInt(value);
    }

    @Override
    public boolean update(RestaurantPojo restaurant) {
        logger.debug("Updating views and likes for list #" + list.getID());

        String query = "UPDATE Menus " + 
                "SET numViews='" + list.getNumViews() + "', numLikes='" + list.getNumLikes() + "' " +
                "WHERE listID='" + list.getID() + "'";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        return (rows != null);
    }

    public void disconnect() {
        SQLUtils.disconnect(conn);
        conn = null;
    }
    

    // =====================================================================

    private ArrayList<RestaurantPojo> convertRowToList(SQLRow row) {
        ArrayList<RestaurantPojo> restaurants = new ArrayList<RestaurantPojo>();
        int restaurantID = Integer.parseInt(row.getItem("restaurantID"));
        String description = row.getItem("description");
        return restaurants;
    }
}