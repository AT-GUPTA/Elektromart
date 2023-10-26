package com.elektromart.dao;

import com.elektromart.domain.Cart;
import com.elektromart.domain.Product;
import com.elektromart.utilis.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

    public boolean addProductToCart(String cartId, String productSlug, int quantity) {
        String SQL = "INSERT INTO CartProduct(cart_id, product_slug, quantity) VALUES(?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, cartId);
            stmt.setString(2, productSlug);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


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

    public List<Product> getCartProducts(String cartId) {
        List<Product> products = new ArrayList<>();

        String SQL = "SELECT p.id, p.url_slug, p.name, p.description, p.price, cp.quantity " +
                "FROM CartProduct cp JOIN Product p ON cp.product_slug = p.url_slug " +
                "WHERE cp.cart_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, cartId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setUrlSlug(rs.getString("url_slug"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getFloat("price"));
                    product.setQuantity(rs.getInt("quantity"));

                    products.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Consider using a logger here.
        }

        return products;
    }


    public int getProductQuantity(String cartId, String productSlug) {
        String SQL = "SELECT quantity FROM CartProduct WHERE cart_id = ? AND product_slug = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, cartId);
            stmt.setString(2, productSlug);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public boolean changeItemQuantity(String cartId, String productSlug, int quantity) {
        String SQL = "UPDATE CartProduct SET quantity = ? WHERE cart_id = ? AND product_slug = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, quantity);
            stmt.setString(2, cartId);
            stmt.setString(3, productSlug);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFromCart(String cartId, String productSlug) {
        String SQL = "DELETE FROM CartProduct WHERE cart_id = ? AND product_slug = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setString(1, cartId);
            stmt.setString(2, productSlug);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}