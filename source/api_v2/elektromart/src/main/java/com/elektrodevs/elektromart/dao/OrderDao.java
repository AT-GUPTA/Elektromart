package com.elektrodevs.elektromart.dao;

import com.elektrodevs.elektromart.domain.Order;
import com.elektrodevs.elektromart.dto.OrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
@Slf4j
public class OrderDao {

    private final JdbcTemplate jdbcTemplate;

    public List<OrderResult> getAllOrders() {
        String SQL = "SELECT * FROM Orders INNER JOIN Users ON Users.user_id = Orders.user_id";
        try {
            List<OrderResult> orders = jdbcTemplate.query(SQL, (rs, rowNum) -> {
                OrderResult order = new OrderResult();
                order.setUsername(rs.getString("username"));
                order.setOrderId(rs.getLong("order_id"));
                order.setUserId(rs.getLong("user_id"));
                order.setCartId(rs.getString("cart_id"));
                order.setCreatedDate(rs.getDate("createdDate"));
                order.setShippingStatus(rs.getString("shippingStatus"));
                order.setShippingAddress(rs.getString("shippingAddress"));
                order.setShippingId(rs.getLong("shipping_id"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                return order;
            });
            log.debug("getAllOrders: Retrieved {} orders from the database.", orders.size());
            return orders;
        } catch (DataAccessException e) {
            log.error("getAllOrders: An error occurred while retrieving orders from the database.", e);
            return null;
        }
    }
    public boolean createOrder(Order order) {
        String SQL = "INSERT INTO Orders (user_id, cart_id, createdDate, shippingStatus, shippingAddress, shipping_id, paymentMethod) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(SQL, order.getUserId(), order.getCartId(), new Date(), order.getShippingStatus(),
                    order.getShippingAddress(), order.getShippingId(), order.getPaymentMethod());
            log.debug("createOrder: Order created successfully for user with ID {}.", order.getUserId());
            return true;
        } catch (DataAccessException e) {
            log.error("createOrder: An error occurred while creating an order for user with ID {}.", order.getUserId(), e);
            return false;
        }
    }

    public List<Order> getOrdersByUserId(String userId) {
        String SQL = "SELECT * FROM Orders WHERE user_id = ?";
        try {
            List<Order> orders = jdbcTemplate.query(SQL, new Object[]{userId}, (rs, rowNum) -> {
                Order order = new Order();
                order.setOrderId(rs.getLong("order_id"));
                order.setUserId(rs.getLong("user_id"));
                order.setCartId(rs.getString("cart_id"));
                order.setCreatedDate(rs.getDate("createdDate"));
                order.setShippingStatus(rs.getString("shippingStatus"));
                order.setShippingAddress(rs.getString("shippingAddress"));
                order.setShippingId(rs.getLong("shipping_id"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                return order;
            });
            log.debug("getOrdersByUserId: Retrieved {} orders for user with ID {}.", orders.size(), userId);
            return orders;
        } catch (DataAccessException e) {
            log.error("getOrdersByUserId: An error occurred while retrieving orders for user with ID {}.", userId, e);
            return null;
        }
    }

    public boolean updateCartIdForUser(String userId, String newCartId) {
        String SQL = "UPDATE Users SET cart_id = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(SQL, newCartId, userId);
            log.debug("updateCartIdForUser: Updated cart_id for user with ID {} to {}.", userId, newCartId);
            return true;
        } catch (DataAccessException e) {
            log.error("updateCartIdForUser: An error occurred while updating cart_id for user with ID {}.", userId, e);
            return false;
        }
    }

}
