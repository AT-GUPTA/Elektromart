package com.elektrodevs.elektromart.dao;

import com.elektrodevs.elektromart.domain.Cart;
import com.elektrodevs.elektromart.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public boolean addProductToCart(String cartId, String productSlug, int quantity) {
        String SQL = "INSERT INTO CartProduct(cart_id, product_slug, quantity) VALUES(?, ?, ?)";
        try {
            jdbcTemplate.update(SQL, cartId, productSlug, quantity);
            log.debug("Product {} added to cart {} with quantity {}.", productSlug, cartId, quantity);
            return true;
        } catch (DataAccessException e) {
            log.error("Failed to add product {} to cart {}.", productSlug, cartId);
            return false;
        }
    }

    public Cart getCartById(String cartId) {
        String SQL = "SELECT * FROM Cart WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{cartId}, (rs, rowNum) -> {
                Cart cart = new Cart();
                cart.setId(rs.getString("id"));
                cart.setUserId(rs.getLong("user_id"));
                cart.setTempId(rs.getString("temp_id"));
                return cart;
            });
        } catch (DataAccessException e) {
            log.error("Failed to get cart by ID: {}", cartId);
            return null;
        }
    }

    public List<Product> getCartProducts(String cartId) {
        String SQL = "SELECT p.id, p.url_slug, p.name, p.description, p.price, cp.quantity " +
                "FROM CartProduct cp JOIN Product p ON cp.product_slug = p.url_slug " +
                "WHERE cp.cart_id = ?";
        try {
            return jdbcTemplate.query(SQL, new Object[]{cartId}, (rs, rowNum) -> {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setUrlSlug(rs.getString("url_slug"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getFloat("price"));
                product.setQuantity(rs.getInt("quantity"));
                return product;
            });
        } catch (DataAccessException e) {
            log.error("Failed to get products for cart with ID: {}", cartId);
            return null;
        }
    }

    public int getProductQuantity(String cartId, String productSlug) {
        String SQL = "SELECT quantity FROM CartProduct WHERE cart_id = ? AND product_slug = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{cartId, productSlug}, Integer.class);
        } catch (DataAccessException e) {
            log.error("Failed to get quantity for product {} in cart with ID: {}", productSlug, cartId);
            return 0;
        }
    }

    public boolean changeItemQuantity(String cartId, String productSlug, int quantity) {
        String SQL = "UPDATE CartProduct SET quantity = ? WHERE cart_id = ? AND product_slug = ?";
        try {
            jdbcTemplate.update(SQL, quantity, cartId, productSlug);
            log.debug("Changed quantity for product {} in cart {} to {}", productSlug, cartId, quantity);
            return true;
        } catch (DataAccessException e) {
            log.error("Failed to change quantity for product {} in cart {} to {}", productSlug, cartId, quantity);
            return false;
        }
    }

    public boolean deleteFromCart(String cartId, String productSlug) {
        String SQL = "DELETE FROM CartProduct WHERE cart_id = ? AND product_slug = ?";
        try {
            jdbcTemplate.update(SQL, cartId, productSlug);
            log.debug("Deleted product {} from cart {}", productSlug, cartId);
            return true;
        } catch (DataAccessException e) {
            log.error("Failed to delete product {} from cart {}", productSlug, cartId);
            return false;
        }
    }

    public boolean createCart(String cartId) {
        try {
            String SQL = "INSERT INTO Cart(id) VALUES (?)";
            jdbcTemplate.update(SQL, cartId);
            log.debug("Created new cart with ID: {}", cartId);
            return true;
        }catch (DataAccessException e){
            log.error("Failed to create a new cart with ID: {}", cartId);
            return false;
        }
    }
    public boolean clearCart(String cartId) {
        String SQL = "DELETE FROM CartProduct WHERE cart_id = ?";
        try {
            jdbcTemplate.update(SQL, cartId);
            log.debug("Cleared cart with ID: {}", cartId);
            return true;
        } catch (DataAccessException e) {
            log.error("Failed to clear cart with ID: {}", cartId);
            return false;
        }
    }
}
