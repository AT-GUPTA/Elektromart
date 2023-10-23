package com.elektromart.dao;

import com.elektromart.domain.Cart;
import com.elektromart.utilis.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartDao {

    public Cart getCartById(String cartId) {
        String SQL = "SELECT * FROM Cart WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, cartId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getString("id"));
                cart.setUserId(rs.getLong("user_id"));
                cart.setTempId(rs.getString("temp_id"));
                return cart;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // todo add methods for changeItemQuantity and delete-from-cart here.
}
