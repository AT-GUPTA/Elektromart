package com.elektrodevs.elektromart.dao;

import com.elektrodevs.elektromart.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class OrderDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Order> getAllOrders() {
        String SQL = "SELECT * FROM Order";
        try {
            return jdbcTemplate.query(SQL, (rs, rowNum) -> {
                Order order = new Order();
                order.setOrderId(rs.getLong("order_id"));
                order.setUserId(rs.getLong("user_id"));
                order.setCartId(rs.getString("cart_id"));
                order.setCreatedDate(rs.getDate("createdDate"));
                order.setShippingStatus(Order.ShippingStatus.valueOf(rs.getString("shippingStatus")));
                order.setShippingAddress(rs.getString("shippingAddress"));
                order.setShippingId(rs.getLong("shipping_id"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                return order;
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean createOrder(Order order) {
        String SQL = "INSERT INTO Order(user_id, cart_id, createdDate, shippingStatus, shippingAddress, shipping_id, paymentMethod) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(SQL, order.getUserId(), order.getCartId(), new Date(), order.getShippingStatus().name(),
                    order.getShippingAddress(), order.getShippingId(), order.getPaymentMethod());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getOrdersByUserId(Long userId) {
        String SQL = "SELECT * FROM Order WHERE user_id = ?";
        try {
            return jdbcTemplate.query(SQL, new Object[]{userId}, (rs, rowNum) -> {
                Order order = new Order();
                order.setOrderId(rs.getLong("order_id"));
                order.setUserId(rs.getLong("user_id"));
                order.setCartId(rs.getString("cart_id"));
                order.setCreatedDate(rs.getDate("createdDate"));
                order.setShippingStatus(Order.ShippingStatus.valueOf(rs.getString("shippingStatus")));
                order.setShippingAddress(rs.getString("shippingAddress"));
                order.setShippingId(rs.getLong("shipping_id"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                return order;
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateCartIdForUser(Long userId, String newCartId) {
        String SQL = "UPDATE Users SET cart_id = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(SQL, newCartId, userId);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

}
