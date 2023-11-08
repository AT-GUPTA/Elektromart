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

}
