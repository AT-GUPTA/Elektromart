package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.Order;
import com.elektrodevs.elektromart.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @GetMapping("/all-orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Order order, HttpSession session) {
        Long userId = (Long) session.getAttribute("id");

        if (userId != null) {
            boolean orderCreated = orderService.createOrder(order, userId);

            if (orderCreated) {
                // Order created successfully
                return ResponseEntity.ok("Order created successfully.");
            } else {
                // Error creating the order or updating cart_id
                return ResponseEntity.badRequest().body("Failed to create the order.");
            }
        } else {
            // User is not logged in
            return ResponseEntity.status(401).body("User is not logged in.");
        }
    }
   // to get all orders for the current user logged in
    @GetMapping("/order-history")
    public ResponseEntity<List<Order>> getOrderHistoryForCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("id");

        if (userId != null) {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } else {
            // User is not logged in
            return ResponseEntity.status(401).body(null);
        }
    }

    // to get all orders by a specific user maybe used to filter search in admin page
    @GetMapping("/get-orders-byId/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);

        if (orders != null) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
