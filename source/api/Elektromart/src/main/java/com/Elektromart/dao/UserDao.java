package com.elektromart.dao;

import com.elektromart.domain.User;
import com.elektromart.utilis.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDao {

    public User findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRoleId(rs.getLong("role_id"));
                user.setStatus(rs.getString("status"));
                user.setCartId(rs.getString("cart_id"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User createUser(User newUser) {
        String cartId = UUID.randomUUID().toString();

        String query = "INSERT INTO Cart (id) VALUES (?)"; // Insert a new cart record
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement cartStatement = connection.prepareStatement(query)) {

            cartStatement.setString(1, cartId);
            int cartInsertResult = cartStatement.executeUpdate();

            if (cartInsertResult > 0) {
                String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());

                query = "INSERT INTO Users (username, email, password, role_id, status, cart_id) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement userStatement = connection.prepareStatement(query)) {
                    userStatement.setString(1, newUser.getUsername());
                    userStatement.setString(2, newUser.getEmail());
                    userStatement.setString(3, hashedPassword);
                    userStatement.setLong(4, newUser.getRoleId());
                    userStatement.setString(5, newUser.getStatus());
                    userStatement.setString(6, cartId);

                    int userInsertResult = userStatement.executeUpdate();

                    if (userInsertResult > 0) {
                        // User creation successful, return the cart ID along with user information
                        newUser.setCartId(cartId);
                        return newUser;
                    }
                } catch (SQLException e) {
                    // Check if error is because of UNIQUE constraint violation
                    if (e.getErrorCode() == 1062) { // 1062 -- MySQL error code for duplicate entry
                        throw new IllegalArgumentException("Username or Email already exists.");
                    } else {
                        throw e; // If not, just re-throw the exception
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}