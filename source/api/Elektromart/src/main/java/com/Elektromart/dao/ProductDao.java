package com.elektromart.dao;

import com.elektromart.domain.Product;
import com.elektromart.utilis.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    public List<Product> getProducts() {
        String SQL = "SELECT * FROM Product";
        List<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setVendor(rs.getString("vendor"));
                product.setUrlSlug(rs.getString("url_slug"));
                product.setSku(rs.getString("sku"));
                product.setPrice(rs.getFloat("price"));
                product.setDiscountPercent(rs.getInt("discount_percent"));
                product.setIsFeatured(rs.getString("is_featured"));
                product.setIsDelete(rs.getString("is_delete"));

                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    // Add other methods as needed
}
