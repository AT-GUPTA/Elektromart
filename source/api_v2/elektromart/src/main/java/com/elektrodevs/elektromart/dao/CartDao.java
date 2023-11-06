package com.elektrodevs.elektromart.dao;

import com.elektrodevs.elektromart.domain.Cart;
import com.elektrodevs.elektromart.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public boolean addProductToCart(String cartId, String productSlug, int quantity) {
        String SQL = "INSERT INTO CartProduct(cart_id, product_slug, quantity) VALUES(?, ?, ?)";
        try {
            jdbcTemplate.update(SQL, cartId, productSlug, quantity);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }

    public int getProductQuantity(String cartId, String productSlug) {
        String SQL = "SELECT quantity FROM CartProduct WHERE cart_id = ? AND product_slug = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{cartId, productSlug}, Integer.class);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean changeItemQuantity(String cartId, String productSlug, int quantity) {
        String SQL = "UPDATE CartProduct SET quantity = ? WHERE cart_id = ? AND product_slug = ?";
        try {
            jdbcTemplate.update(SQL, quantity, cartId, productSlug);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFromCart(String cartId, String productSlug) {
        String SQL = "DELETE FROM CartProduct WHERE cart_id = ? AND product_slug = ?";
        try {
            jdbcTemplate.update(SQL, cartId, productSlug);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createCart(String cartId) {
        try {
            String SQL = "INSERT INTO Cart(id) VALUES (?)";
            jdbcTemplate.update(SQL, cartId);
            return true;
        }catch (DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }
}
