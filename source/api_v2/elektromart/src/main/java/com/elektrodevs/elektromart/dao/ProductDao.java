package com.elektrodevs.elektromart.dao;

import com.elektrodevs.elektromart.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
@RequiredArgsConstructor
@Slf4j
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
        String query = "SELECT * FROM Product";
        try {
            List<Product> products = jdbcTemplate.query(query, productRowMapper);
            log.debug("getProducts: Retrieved {} products from the database.", products.size());
            return products;
        } catch (DataAccessException e) {
            log.error("getProducts: An error occurred while retrieving products from the database.", e);
            return null;
        }
    }
    public Product createProduct(Product newProduct) {
        newProduct.setSku(generateSKU()); // Generate SKU
        newProduct.setUrlSlug(generateUrlSlug(newProduct.getName())); // Generate URL Slug

        String query = "INSERT INTO Product (name, description, vendor, url_slug, sku, price, discount_percent, is_featured, is_delete) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(query,
                    newProduct.getName(),
                    newProduct.getDescription(),
                    newProduct.getVendor(),
                    newProduct.getUrlSlug(), // Use generated URL slug
                    newProduct.getSku(), // Use generated SKU
                    newProduct.getPrice(),
                    newProduct.getDiscountPercent(),
                    newProduct.isFeatured(),
                    newProduct.isDelete());

            int lastInsertId = getLastInsertId();
            newProduct.setId(lastInsertId);
            log.debug("createProduct: Product created successfully with ID {}.", lastInsertId);
            return newProduct;
        } catch (DataAccessException e) {
            log.error("createProduct: An error occurred while creating a product.", e);
            return null;
        }
    }

    private String generateSKU() {
        StringBuilder sku = new StringBuilder("SKU-");
        Random random = new Random();
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < 9; i++) {
            int index = random.nextInt(chars.length());
            sku.append(chars.charAt(index));
        }

        // Append the last three parts (xx-xxx) of SKU
        sku.append("-")
                .append(String.format("%02d", random.nextInt(100)))
                .append("-")
                .append(String.format("%03d", random.nextInt(1000)));

        return sku.toString();
    }

    private String generateUrlSlug(String productName) {
        // Make the product name lowercase and replace spaces with dashes
        return productName.toLowerCase().replaceAll(" ", "-");
    }

    public Product editProduct(Product updatedProduct) {
        Product product = getProductById(updatedProduct.getId());
        if(product == null){
            return null;
        }
        String query = "UPDATE Product SET name = ?, description = ?, vendor = ?, url_slug = ?, sku = ?, price = ?, " +
                "discount_percent = ?, is_featured = ?, is_delete = ? WHERE id = ?";
        try{
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
            log.debug("editProduct: Product with ID {} updated successfully.", updatedProduct.getId());
            return updatedProduct;
        } catch (DataAccessException e){
            log.error("editProduct: An error occurred while updating a product with ID {}.", updatedProduct.getId(), e);
            return null;
        }

    }

    public Product getProductById(int productId) {
        String query = "SELECT * FROM Product WHERE id = ?";
        try {
            Product product = jdbcTemplate.queryForObject(query, new Object[]{productId}, productRowMapper);
            log.debug("getProductById: Retrieved product with ID {}.", productId);
            return product;
        } catch (DataAccessException e) {
            log.error("getProductById: An error occurred while retrieving product with ID {}.", productId, e);
            return null;
        }
    }

    private int getLastInsertId() {
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    public Product getProductBySlug(String urlSlug) {
        try {
            Product product = jdbcTemplate.queryForObject("SELECT * FROM Product WHERE url_slug = ?", new Object[]{urlSlug}, productRowMapper);
            log.debug("getProductBySlug: Retrieved product with URL Slug {}.", urlSlug);
            return product;
        } catch (DataAccessException e) {
            log.error("getProductBySlug: An error occurred while retrieving product with URL Slug {}.", urlSlug, e);
            return null;
        }
    }
}
