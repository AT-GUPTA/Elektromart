package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.CartDao;
import com.elektrodevs.elektromart.dao.OrderDao;
import com.elektrodevs.elektromart.domain.Order;
import com.elektrodevs.elektromart.dto.OrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderDao orderDao;
    private final CartDao cartDao;
    public List<OrderResult> getAllOrders() {
        List<OrderResult> orders = orderDao.getAllOrders();
        log.debug("getAllOrders: Retrieved {} orders.", orders.size());
        return orders;
    }
    public String createOrder(String cartId, String deliveryAddress, String userId) {

        Order order = new Order(cartId, deliveryAddress, userId);
        boolean orderCreated = orderDao.createOrder(order);

        if (orderCreated) {
            // Update the user's cart_id with a new UUID
            String newCartId = UUID.randomUUID().toString();
            boolean cartCreated = cartDao.createCart(newCartId);
            boolean cartIdUpdated = orderDao.updateCartIdForUser(userId, newCartId);

            if (cartIdUpdated) {
                log.debug("createOrder: Order created and user's cart_id updated successfully.");
                return newCartId;
            } else {
                log.error("createOrder: Error updating the user's cart_id.");
                return "";
            }
        } else {
            log.error("createOrder: Error creating the order.");
            return "";
        }
    }

    public List<Order> getOrdersByUserId(String userId) {
        List<Order> orders = orderDao.getOrdersByUserId(userId);
        log.debug("getOrdersByUserId: Retrieved {} orders for user with ID '{}'.", orders.size(), userId);
        return orders;
    }
    public boolean updateShippingStatus(Long orderId, String newStatus) {
        try {
            // Check if the newStatus is a valid value, e.g., 'PENDING', 'SHIPPED', 'DELIVERED'
            if (isValidShippingStatus(newStatus)) {
                boolean updated = orderDao.updateShippingStatus(orderId, newStatus);
                if (updated) {
                    log.debug("updateShippingStatus: Updated shipping status for order ID '{}'.", orderId);
                    return true;
                } else {
                    log.error("updateShippingStatus: Failed to update shipping status for order ID '{}'.", orderId);
                    return false;
                }
            } else {
                log.error("updateShippingStatus: Invalid shipping status value: '{}'.", newStatus);
                return false;
            }
        } catch (Exception e) {
            log.error("updateShippingStatus: An error occurred while updating shipping status for order ID '{}'.", orderId, e);
            return false;
        }
    }

    public boolean updateTrackingNumber(Long orderId, long trackingNumber) {
        try {
            boolean updated = orderDao.updateTrackingNumber(orderId, trackingNumber);
            if (updated) {
                log.debug("updateShippingStatus: Updated tracking number for order ID '{}'.", orderId);
                return true;
            } else {
                log.error("updateShippingStatus: Failed to update tracking number for order ID '{}'.", orderId);
                return false;
            }
        } catch (Exception e) {
            log.error("updateShippingStatus: An error occurred while updating tracking number for order ID '{}'.", orderId, e);
            return false;
        }
    }

    private boolean isValidShippingStatus(String status) {
        return status != null && ("PENDING".equals(status) || "SHIPPED".equals(status) || "DELIVERED".equals(status));
    }

    public Order getOrderByOrderId(Long orderId) {
        return orderDao.getOrderByOrderId(orderId);
    }

    public boolean updateUserForOrderIfAbsent(Long orderId, Long userId) {
        log.debug("updateUserForOrderIfAbsent: Attempting to update user for order ID '{}'", orderId);
        return orderDao.updateUserForOrderIfAbsent(orderId, userId);
    }

}
