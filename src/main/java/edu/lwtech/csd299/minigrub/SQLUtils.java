package edu.lwtech.csd299.minigrub;

import java.sql.*;
import java.util.*;

import org.apache.log4j.*;

/* 
 * This code was taken from Chip Anderson's topten-lists-servlet project 
 * Modified by: Beni Ungur
 */

public class SQLUtils {
    private static final Logger logger = Logger.getLogger(SQLUtils.class);

    private SQLUtils() { }                                          // Hide the implicit public constructor

    public static Connection connect(String url, String user, String password, String driverClass) {
        String connString = url + " User: " + user + ", Password: " + password;
        logger.debug("Connecting to " + connString + "...");
        
        Connection conn = null;

        try {
            Class.forName(driverClass);                             // Dynamically loads the driver from the WAR file
        } catch (ClassNotFoundException e) {
            logger.error("Unable to find JDBC driver on classpath: " + driverClass , e);
            return null;
        }

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("Unable to connect to SQL Database with JDBC string: " + connString, e);
            return null;
        }

        logger.debug("Connected!");
        return conn;
    }

    public static List<SQLRow> executeSQL(Connection conn, String query, String... arguments) {
        logger.debug("Executing SQL statement: " + query);

        try {
            // Create the new statement object
            PreparedStatement stmt = conn.prepareStatement(query);

            // Substitute in the argument values for the question marks
            int position = 1;
            for (String arg : arguments) {
                stmt.setString(position++, arg);
            }

            query = query.toLowerCase();
            if (query.contains("update ") || query.contains("delete ")) {

                int numRows = stmt.executeUpdate();
                return results(numRows);

            } else if (query.contains("select ")) {

                // Execute the SELECT query
                ResultSet sqlResults = stmt.executeQuery();

                // Get the column names
                ResultSetMetaData md = sqlResults.getMetaData();
                List<String> columns = new ArrayList<>();
                for (int i=0; i < md.getColumnCount(); i++) {
                    columns.add(md.getColumnName(i+1));
                }

                // Store each row in a List
                List<SQLRow> rows = new ArrayList<>();
                while (sqlResults.next()) {
                    SQLRow row = new SQLRow(columns, sqlResults);
                    logger.debug(row.toString());
                    rows.add(row);
                }

                return rows;
            }
        } catch (SQLException e) {
            logger.error("SQL Exception caught in executeSQL: " + query, e);
            return null;
        }
        return null;
    }


    public static int executeSQLInsert(Connection conn, String query, String recID, String... arguments) {
        logger.debug("Executing SQL Insert: " + query);

        int newID = -1;
        String[] returnColumns = new String[] { recID };

        try {
            // Create the new statement object, specifying the recID return column as well
            PreparedStatement stmt = conn.prepareStatement(query, returnColumns);

            // Substitute in the argument values for the question marks
            int position = 1;
            for (String arg : arguments)
                stmt.setString(position++, arg);

            // Execute the INSERT statement
            stmt.executeUpdate();
            
            // Get the new recID value from the query results and return it to the caller
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            newID = keys.getInt(1);
        } catch (SQLException e) {
            logger.error("SQL Exception caught in executeSQLInsert: " + query, e);
            return -1;
        }

        return newID;
    }

    public static void disconnect(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            logger.error("Exception thrown while trying to close SQL Connection", e);
        }
    }

    // ===============================================================================================

    private static List<SQLRow> results(int i) {
        List<SQLRow> rows = new ArrayList<>();
        rows.add(new SQLRow("Value", i));
        return rows;
    }
}