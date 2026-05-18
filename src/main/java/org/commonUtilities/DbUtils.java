package org.commonUtilities;
//A single ton class patterned Data Base utility with ThreadLocal implementation for the OWN thread-own connection model
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.config.ConfigManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtils {

    private static final Logger logger = LogManager.getLogger(DbUtils.class);
    private static volatile DbUtils instance;
    private static final ThreadLocal<Connection> threadConnection = new ThreadLocal<>();
    private final ConfigManager config = ConfigManager.getInstance();

    private DbUtils(){}

    public static DbUtils getInstance() {
        if (instance == null) {
            synchronized (DbUtils.class) {
                if (instance == null) {
                    instance = new DbUtils();
                    logger.info("DbUtils Singleton instance created");
                }
            }
        }
        return instance;
    }

    public void openDBConnection() {
        if (threadConnection.get() != null) {
            logger.warn("Connection already open for thread: {} — skipping",
                    Thread.currentThread().getName());
            return;
        }
        try {
            String driver   = config.getDbDriver();
            String url      = config.getDbUrl();
            String username = config.getDbUsername();
            String password = config.getDbPassword();
            logger.info("Opening DB connection.....");
            logger.info("Environment : {}", config.getEnvironment());
            logger.info("DB URL      : {}", url);
            logger.info("DB Username : {}", username);
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            threadConnection.set(conn); // Bind connection to THIS thread
            logger.info("DB connection opened successfully for thread: {}",
                    Thread.currentThread().getName());
        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver class not found: {}", e.getMessage());
            throw new RuntimeException("JDBC Driver not found: " + e.getMessage(), e);
        } catch (SQLException e) {
            logger.error("Failed to open DB connection: {}", e.getMessage());
            throw new RuntimeException("DB connection failed: " + e.getMessage(), e);
        }
    }

    public void closeConnection() {
        Connection conn = threadConnection.get();
        if (conn != null) {
            try {
                conn.close();
                threadConnection.remove();
                logger.info("DB connection closed for thread: {}",
                        Thread.currentThread().getName());
            } catch (SQLException e) {
                logger.error("Failed to close DB connection: {}", e.getMessage());
            }
        } else {
            logger.warn("closeConnection called but no connection found for thread: {}",
                    Thread.currentThread().getName());
        }
    }


    private Connection getConnection() {
        Connection conn = threadConnection.get();
        if (conn == null) {
            logger.warn("No DB connection for thread: {} — opening now",
                    Thread.currentThread().getName());
            openDBConnection();
            conn = threadConnection.get();
        }
        try {
            if (conn.isClosed()) {
                logger.warn("DB connection was closed — reopening for thread: {}",
                        Thread.currentThread().getName());
                threadConnection.remove();
                openDBConnection();
                conn = threadConnection.get();
            }
        } catch (SQLException e) {
            logger.error("Connection health check failed: {}", e.getMessage());
        }
        return conn;
    }

    // =================================================
    // 1. GET SINGLE VALUE
    // Returns ONE cell value from query result
    //
    // Use: "SELECT name FROM users WHERE id=1"
    // Returns: "value"
    // =================================================
    public String getSingleDBValue(String query) {
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs   = stmt.executeQuery(query))
        {
            if (rs.next()) {
                String value = rs.getString(1);
                logger.info("getSingleValue result: {}", value);
                return value;
            }
            logger.warn("getSingleValue returned no results for query: {}", query);
        } catch (SQLException e) {
            logger.error("getSingleValue failed: {}", e.getMessage());
            throw new RuntimeException("getSingleValue failed: " + e.getMessage(), e);
        }
        return null;
    }

    // =================================================
    // 2. GET SINGLE ROW
    // Returns one full row as column → value Map
    // Use: "SELECT * FROM users WHERE id=1"
    // =================================================
    public Map<String, String> getSingleRowData(String query) {
        logger.info("getSingleRow | Thread: {} | Query: {}",
                Thread.currentThread().getName(), query);
        Map<String, String> rowData = new HashMap<>();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query))
        {
            ResultSetMetaData meta  = rs.getMetaData();
            int columnCount         = meta.getColumnCount();
            if (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(meta.getColumnName(i), rs.getString(i));
                }
                logger.info("getSingleRow returned {} columns", columnCount);
            } else {
                logger.warn("getSingleRow returned no results for query: {}", query);
            }
        } catch (SQLException e) {
            logger.error("getSingleRow failed: {}", e.getMessage());
            throw new RuntimeException("getSingleRow failed: " + e.getMessage(), e);
        }
        return rowData;
    }

    // =================================================
    // 3. GET MULTIPLE ROWS
    // Returns all rows as List of Maps
    // Use: "SELECT * FROM users WHERE status='active'"
    // Returns: [{id:"value1",name:"value2"}, {id:"2",name:"Ram"}]
    // =================================================
    public List<Map<String, String>> getMultipleRowsData(String query) {
        logger.info("getMultipleRows | Thread: {} | Query: {}",
                Thread.currentThread().getName(), query);
        List<Map<String, String>> allRows = new ArrayList<>();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs= stmt.executeQuery(query))
        {
            ResultSetMetaData meta        = rs.getMetaData();
            int columnCount               = meta.getColumnCount();

            while (rs.next()) {
                Map<String, String> row   = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), rs.getString(i));
                }
                allRows.add(row);
            }
            logger.info("getMultipleRows returned {} rows", allRows.size());
        } catch (SQLException e) {
            logger.error("getMultipleRows failed: {}", e.getMessage());
            throw new RuntimeException("getMultipleRows failed: " + e.getMessage(), e);
        }
        return allRows;
    }

    // =================================================
    // 4. GET SINGLE COLUMN
    // Returns all values from one column as List
    // Use: "SELECT email FROM users"
    // =================================================
    public List<String> getSingleColumn(String query) {
        logger.info("getSingleColumn | Thread: {} | Query: {}",
                Thread.currentThread().getName(), query);
        List<String> columnData = new ArrayList<>();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query))
        {
            while (rs.next()) {
                columnData.add(rs.getString(1));
            }
            logger.info("getSingleColumn returned {} values", columnData.size());

        } catch (SQLException e) {
            logger.error("getSingleColumn failed: {}", e.getMessage());
            throw new RuntimeException("getSingleColumn failed: " + e.getMessage(), e);
        }
        return columnData;
    }

    // =================================================
    // 5. GET ROW COUNT
    // Returns number of rows matching query
    // =================================================
    public int getRowCount(String query) {
        logger.info("getRowCount | Thread: {} | Query: {}",
                Thread.currentThread().getName(), query);
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs   = stmt.executeQuery(query)) {

            if (rs.next()) {
                int count = rs.getInt(1);
                logger.info("getRowCount result: {}", count);
                return count;
            }

        } catch (SQLException e) {
            logger.error("getRowCount failed: {}", e.getMessage());
            throw new RuntimeException("getRowCount failed: " + e.getMessage(), e);
        }
        return 0;
    }

    // =================================================
    // 6. IS RECORD PRESENT
    // Returns true if at least one row found,Returns: true / false
    // =================================================
    public boolean isRecordPresent(String query) {
        logger.info("isRecordPresent | Thread: {} | Query: {}",
                Thread.currentThread().getName(), query);
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs   = stmt.executeQuery(query)) {
            boolean present = rs.next();
            logger.info("isRecordPresent result: {}", present);
            return present;
        } catch (SQLException e) {
            logger.error("isRecordPresent failed: {}", e.getMessage());
            throw new RuntimeException("isRecordPresent failed: " + e.getMessage(), e);
        }
    }
    // =================================================
    // 7. EXECUTE UPDATE — INSERT / UPDATE / DELETE
    // Returns number of rows affected
    // Use: "INSERT", "UPDATE" or "DELETE"
    // =================================================
    public int executeUpdate(String query) {
        logger.info("executeUpdate | Thread: {} | Query: {}",
                Thread.currentThread().getName(), query);
        try (Statement stmt  = getConnection().createStatement()) {
            int rowsAffected = stmt.executeUpdate(query);
            logger.info("executeUpdate — rows affected: {}", rowsAffected);
            return rowsAffected;

        } catch (SQLException e) {
            logger.error("executeUpdate failed: {}", e.getMessage());
            throw new RuntimeException("executeUpdate failed: " + e.getMessage(), e);
        }
    }


    // =================================================
    // 9. VERIFY VALUE IN DB
    // Returns: true if DB value matches expected
    // =================================================
    public boolean verifyValueInDB(String query, String expectedValue) {
        logger.info("verifyValueInDB | Thread: {} | Expected: {}",
                Thread.currentThread().getName(), expectedValue);
        String actualValue = getSingleDBValue(query);
        if (actualValue == null) {
            logger.error("verifyValueInDB — no value found in DB for query: {}", query);
            return false;
        }
        boolean match = expectedValue.equalsIgnoreCase(actualValue.trim());
        logger.info("verifyValueInDB | Expected: {} | Actual: {} | Match: {}",
                expectedValue, actualValue, match);
        return match;
    }

    // =================================================
    // 10. VERIFY ROW EXISTS
    // Returns: true / false
    // =================================================
    public boolean verifyRowExists(String tableName, String condition) {
        String query = "SELECT * FROM " + tableName + " WHERE " + condition;
        logger.info("verifyRowExists | Table: {} | Condition: {}",
                tableName, condition);
        boolean exists = isRecordPresent(query);
        logger.info("verifyRowExists result: {}", exists);
        return exists;
    }

}
