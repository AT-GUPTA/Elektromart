package com.elektrodevs.elektromart.dao;

import com.elektrodevs.elektromart.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setVendor(rs.getString("vendor"));
        product.setUrlSlug(rs.getString("url_slug"));
        product.setSku(rs.getString("sku"));
        product.setPrice(rs.getFloat("price"));
        product.setDiscountPercent(rs.getInt("discount_percent"));
        product.setFeatured(rs.getBoolean("is_featured"));
        product.setDelete(rs.getBoolean("is_delete"));
        return product;
    };

    public List<Product> getProducts() {
        return jdbcTemplate.query("SELECT * FROM Product", productRowMapper);
    }

    public Product createProduct(Product newProduct) {
        String query = "INSERT INTO Product (name, description, vendor, url_slug, sku, price, discount_percent, is_featured, is_delete) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
                newProduct.getName(),
                newProduct.getDescription(),
                newProduct.getVendor(),
                newProduct.getUrlSlug(),
                newProduct.getSku(),
                newProduct.getPrice(),
                newProduct.getDiscountPercent(),
                newProduct.isFeatured(),
                newProduct.isDelete());

        // Assuming we have a method to get the last insert ID
        int lastInsertId = getLastInsertId();
        newProduct.setId(lastInsertId);

        return newProduct;
    }

    public Product editProduct(Product updatedProduct) {
        String query = "UPDATE Product SET name = ?, description = ?, vendor = ?, url_slug = ?, sku = ?, price = ?, " +
                "discount_percent = ?, is_featured = ?, is_delete = ? WHERE id = ?";
        jdbcTemplate.update(query,
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getVendor(),
                updatedProduct.getUrlSlug(),
                updatedProduct.getSku(),
                updatedProduct.getPrice(),
                updatedProduct.getDiscountPercent(),
                updatedProduct.isFeatured(),
                updatedProduct.isDelete(),
                updatedProduct.getId());
        return updatedProduct;
    }

    public Product getProductById(int productId) {
        return jdbcTemplate.queryForObject("SELECT * FROM Product WHERE id = ?", new Object[]{productId}, productRowMapper);
    }

    private int getLastInsertId() {
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }
}
