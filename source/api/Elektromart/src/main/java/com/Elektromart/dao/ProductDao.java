package com.elektromart.dao;

import com.elektromart.domain.Product;
import com.elektromart.utilis.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                product.setId(rs.getInt("id"));
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    public Product createProduct(Product newProduct) {
        String query = "INSERT INTO Product (name, description, vendor, url_slug, sku, price, discount_percent, is_featured, is_delete) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newProduct.getName());
            preparedStatement.setString(2, newProduct.getDescription());
            preparedStatement.setString(3, newProduct.getVendor());
            preparedStatement.setString(4, newProduct.getUrlSlug());
            preparedStatement.setString(5, newProduct.getSku());
            preparedStatement.setFloat(6, newProduct.getPrice());
            preparedStatement.setInt(7, newProduct.getDiscountPercent());
            preparedStatement.setString(8, newProduct.getIsFeatured());
            preparedStatement.setString(9, newProduct.getIsDelete());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return newProduct;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the product creation fails
    }
    public Product editProduct(Product updatedProduct) {
        String query = "UPDATE Product SET name = ?, description = ?, vendor = ?, url_slug = ?, sku = ?, price = ?, " +
                "discount_percent = ?, is_featured = ?, is_delete = ? WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, updatedProduct.getName());
            preparedStatement.setString(2, updatedProduct.getDescription());
            preparedStatement.setString(3, updatedProduct.getVendor());
            preparedStatement.setString(4, updatedProduct.getUrlSlug());
            preparedStatement.setString(5, updatedProduct.getSku());
            preparedStatement.setFloat(6, updatedProduct.getPrice());
            preparedStatement.setInt(7, updatedProduct.getDiscountPercent());
            preparedStatement.setString(8, updatedProduct.getIsFeatured());
            preparedStatement.setString(9, updatedProduct.getIsDelete());
            preparedStatement.setInt(10, updatedProduct.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return updatedProduct;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's error handling strategy
        }
        return null; // Return null if the product editing fails
    }
    public Product getProductById(int productId) {
        String query = "SELECT * FROM Product WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setVendor(resultSet.getString("vendor"));
                    product.setUrlSlug(resultSet.getString("url_slug"));
                    product.setSku(resultSet.getString("sku"));
                    product.setPrice(resultSet.getFloat("price"));
                    product.setDiscountPercent(resultSet.getInt("discount_percent"));
                    product.setIsFeatured(resultSet.getString("is_featured"));
                    product.setIsDelete(resultSet.getString("is_delete"));
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's error handling strategy
        }
        return null; // Return null if the product retrieval fails
    }
}

