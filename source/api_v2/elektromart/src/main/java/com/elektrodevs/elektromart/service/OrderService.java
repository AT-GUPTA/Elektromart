package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.OrderDao;
import com.elektrodevs.elektromart.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }
    public Boolean createOrder(Order order, Long userId) {
        boolean orderCreated = orderDao.createOrder(order);

        if (orderCreated) {
            // Update the user's cart_id with a new UUID
            String newCartId = UUID.randomUUID().toString();
            boolean cartIdUpdated = orderDao.updateCartIdForUser(userId, newCartId);

            if (cartIdUpdated) {
                // Cart ID updated successfully
                return true;
            } else {
                // Error updating the user's cart_id
                return false;
            }
        } else {
            // Error creating the order
            return false;
        }
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderDao.getOrdersByUserId(userId);
    }


}
