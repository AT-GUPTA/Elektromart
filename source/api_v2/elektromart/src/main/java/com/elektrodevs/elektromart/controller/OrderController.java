package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.Order;
import com.elektrodevs.elektromart.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for order-related operations.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    /**
     * Retrieves all orders.
     *
     * @return A list of all orders.
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER')")
    public ResponseEntity<List<Order>> getAllOrders() {
        log.debug("Request to get all orders");
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Creates a new order.
     *
     * @param order   The order to be created.
     * @param session The HTTP session for the current user.
     * @return A response entity with success or error message.
     */
    @PostMapping("/create-order")
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER')")
    public ResponseEntity<?> createOrder(@RequestBody Order order, HttpSession session) {
        Long userId = (Long) session.getAttribute("id");
        log.debug("Request to create order for user id: {}", userId);

        if (userId != null) {
            boolean orderCreated = orderService.createOrder(order, userId);

            if (orderCreated) {
                log.debug("Order created successfully for user id: {}", userId);
                return ResponseEntity.ok("Order created successfully.");
            } else {
                log.error("Failed to create order for user id: {}", userId);
                return ResponseEntity.badRequest().body("Failed to create the order.");
            }
        } else {
            log.error("Attempt to create order without login");
            return ResponseEntity.status(401).body("User is not logged in.");
        }
    }

    /**
     * Retrieves order history for the current logged-in user.
     *
     * @param userId The user ID from session.
     * @return A list of orders for the user.
     */
    @GetMapping("/order-history")
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER')")
    public ResponseEntity<List<Order>> getOrderHistoryForCurrentUser(@RequestHeader("X-Session-ID") String userId) {
        log.debug("Request to get order history for user id: {}", userId);
        if (userId != null) {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } else {
            log.error("Attempt to access order history without login");
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Retrieves all orders made by a specific user, typically used for administrative purposes.
     *
     * @param userId The ID of the user whose orders are to be retrieved.
     * @return A list of orders for the specified user.
     */
    @GetMapping("/get-orders-byId/{userId}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable String userId) {
        log.debug("Request to get orders for user id: {}", userId);
        List<Order> orders = orderService.getOrdersByUserId(userId);

        if (orders != null) {
            return ResponseEntity.ok(orders);
        } else {
            log.error("No orders found for user id: {}", userId);
            return ResponseEntity.notFound().build();
        }
    }
}