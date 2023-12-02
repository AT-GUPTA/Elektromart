package com.elektrodevs.elektromart.dao;

import com.elektrodevs.elektromart.domain.Order;
import com.elektrodevs.elektromart.dto.OrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());

        try {
            jdbcTemplate.update(SQL, order.getUserId(), order.getCartId(), timestamp, order.getShippingStatus(),
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

    public boolean updateShippingStatus(Long orderId, String newShippingStatus) {
        // Validate that the provided newShippingStatus corresponds to a valid ShippingStatus enum
        if (isValidShippingStatus(newShippingStatus)) {
            String SQL = "UPDATE Orders SET shippingStatus = ? WHERE order_id = ?";
            try {
                int rowsAffected = jdbcTemplate.update(SQL, newShippingStatus, orderId);
                if (rowsAffected > 0) {
                    log.debug("updateShippingStatus: Updated shipping status for order with ID {} to {}.", orderId, newShippingStatus);
                    return true;
                } else {
                    log.debug("updateShippingStatus: No order found with ID {}.", orderId);
                    return false;
                }
            } catch (DataAccessException e) {
                log.error("updateShippingStatus: An error occurred while updating shipping status for order with ID {}.", orderId, e);
                return false;
            }
        } else {
            log.error("updateShippingStatus: Invalid shipping status value received from the UI: {}", newShippingStatus);
            return false;
        }
    }

    public boolean updateTrackingNumber(Long orderId, long trackingNumber) {
        String SQL = "UPDATE Orders SET shipping_id = ? WHERE order_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(SQL, trackingNumber, orderId);
            if (rowsAffected > 0) {
                log.debug("updateTrackingNumber: Updated tracking number for order with ID {} to {}.", orderId, trackingNumber);
                return true;
            } else {
                log.debug("updateTrackingNumber: No order found with ID {}.", orderId);
                return false;
            }
        } catch (DataAccessException e) {
            log.error("updateTrackingNumber: An error occurred while tracking number id for order with ID {}.", orderId, e);
            return false;
        }
    }

    private boolean isValidShippingStatus(String status) {
        for (Order.ShippingStatus shippingStatus : Order.ShippingStatus.values()) {
            if (shippingStatus.name().equals(status)) {
                return true;
            }
        }
        return false;
    }

    public Order getOrderByOrderId(Long orderId) {
        String SQL = "SELECT * FROM Orders WHERE order_id = ?";
        try {
            Order result = jdbcTemplate.queryForObject(SQL, new Object[]{orderId}, (rs, rowNum) -> {
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
            log.debug("getOrderByOrderId: Retrieved order with ID {}.", orderId);
            return result;
        } catch (DataAccessException e) {
            log.error("getOrderByOrderId: An error occurred while retrieving order with ID {}.", orderId, e);
            return null;
        }
    }

    public boolean updateUserForOrderIfAbsent(Long orderId, Long userId) {
        String SQL = "UPDATE Orders SET user_id = ? WHERE order_id = ? AND user_id IS NULL";
        try {
            int rowsAffected = jdbcTemplate.update(SQL, userId, orderId);
            if (rowsAffected > 0) {
                log.debug("updateUserForOrderIfAbsent: Updated user for order with ID {}.", orderId);
                return true;
            } else {
                log.debug("updateUserForOrderIfAbsent: No update performed, either order not found or user already present.");
                return false;
            }
        } catch (DataAccessException e) {
            log.error("updateUserForOrderIfAbsent: An error occurred while updating user for order with ID {}.", orderId, e);
            return false;
        }
    }

}
