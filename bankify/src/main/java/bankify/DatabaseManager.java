package bankify;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.nio.file.Paths;


public class DatabaseManager {
    private static String URL;
    private static String USER;
    private static String PASSWORD; 

    static {
        try {
            Properties props = new Properties();
            String envPath = Paths.get("").toAbsolutePath().toString() + "/.env";

            props.load(new FileInputStream(envPath));

            URL = props.getProperty("DB_URL");
            USER = props.getProperty("DB_USER");
            PASSWORD = props.getProperty("DB_PASSWORD");            
        } catch (IOException e) {
            System.out.println("❌ Error loading database credentials: " + e.getMessage());
        }
    }
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            return null;
        }
    }
}
