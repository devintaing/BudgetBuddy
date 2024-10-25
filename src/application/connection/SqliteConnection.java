package application.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {
    private static final String dbUrl = "jdbc:sqlite:resources/database/BudgetDb.sqlite";

    public static Connection Connector() {
        // Connect to application.database
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(dbUrl);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}