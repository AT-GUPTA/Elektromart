package com.elektromart.dao;

import com.elektromart.domain.User;
import com.elektromart.utilis.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public User findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("userId"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRoleId(rs.getLong("roleId"));
                user.setStatus(rs.getString("status"));
                user.setAuthorized(rs.getBoolean("authorized"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createUser(User newUser) {
        String query = "INSERT INTO users (username, email, password, roleId, status, authorized) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());

            preparedStatement.setString(1, newUser.getUsername());
            preparedStatement.setString(2, newUser.getEmail());
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.setLong(4, newUser.getRoleId());
            preparedStatement.setString(5, newUser.getStatus());
            preparedStatement.setBoolean(6, newUser.isAuthorized()); // assuming isAuthorized is a getter for the authorized field

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Other CRUD operations as needed
}
