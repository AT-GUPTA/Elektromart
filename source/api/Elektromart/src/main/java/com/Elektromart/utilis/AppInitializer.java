package com.elektromart.utilis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            try (Connection conn = DatabaseManager.getConnection()) {
                try (Statement stmt = conn.createStatement()) {
                    // Your initialization queries here e.g.
                    stmt.executeQuery("SELECT 1 FROM PRODUCT");
                    stmt.executeQuery("SELECT 1 FROM CART");
                    stmt.executeQuery("SELECT 1 FROM CARTPRODUCT");
                    stmt.executeQuery("SELECT 1 FROM USERS");
                    stmt.executeQuery("SELECT 1 FROM ROLE");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("AppInitializer.contextDestroyed");
    }
}
