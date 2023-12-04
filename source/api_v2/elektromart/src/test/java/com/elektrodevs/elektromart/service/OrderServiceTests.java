package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.CartDao;
import com.elektrodevs.elektromart.dao.OrderDao;
import com.elektrodevs.elektromart.domain.Order;
import com.elektrodevs.elektromart.dto.OrderResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTests {

    @Mock
    private OrderDao orderDao;

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders() {
        // Arrange
        when(orderDao.getAllOrders()).thenReturn(Collections.emptyList());

        // Act
        List<OrderResult> orders = orderService.getAllOrders();

        // Assert
        assertNotNull(orders);
        assertTrue(orders.isEmpty());
        verify(orderDao, times(1)).getAllOrders();
    }

    @Test
    void createOrder_Failure_OrderCreation() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        String deliveryAddress = "123 Main St";
        String userId = "1";
        Order order = new Order(cartId, deliveryAddress, userId);

        when(orderDao.createOrder(order)).thenReturn(false);

        // Act
        String newCartId = orderService.createOrder(cartId, deliveryAddress, userId);

        // Assert
        assertTrue(newCartId.isEmpty());
    }
}
