package com.elektromart.utilis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {

    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    static {
        Properties properties = new Properties();
        try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("Unable to find application.properties");
            }
            properties.load(input);
            DB_URL = properties.getProperty("elektromart.db.path");
            DB_USER = properties.getProperty("elektromart.db.user");
            DB_PASSWORD = properties.getProperty("elektromart.db.password", ""); // Defaults to empty string if not found
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
