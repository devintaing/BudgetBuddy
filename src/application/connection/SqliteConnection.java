package application.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {
    private static final String dbUrl = "jdbc:sqlite:resources/database/BudgetDb.sqlite";

    public static Connection Connector() {
        // Connect to application.database
        try {
        	// Loads the SQLite JDBC driver class
            Class.forName("org.sqlite.JDBC");
            
            // Establishes a connection to the database
            Connection con = DriverManager.getConnection(dbUrl);
            
            // Returns the established connection
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}