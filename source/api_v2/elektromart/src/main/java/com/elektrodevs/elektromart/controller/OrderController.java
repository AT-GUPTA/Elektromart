package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.Order;
import com.elektrodevs.elektromart.dto.OrderResult;
import com.elektrodevs.elektromart.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for order-related operations.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    /**
     * Retrieves all orders.
     *
     * @return A list of all orders.
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<List<OrderResult>> getAllOrders() {
        log.debug("Request to get all orders");
        List<OrderResult> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Retrieves a specific order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return A response entity with the order.
     */
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<?> getOrderByOrderId(@PathVariable Long orderId) {
        log.debug("Request to get order {}", orderId);
        Order order = orderService.getOrderByOrderId(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            log.error("No order found with id: {}", orderId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new order.
     *
     * @return A response entity with success or error message.
     */
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        Long userId = orderRequest.getUserId();
        String cartId = orderRequest.getCartId();
        String deliveryAddress = orderRequest.getDeliveryAddress();

        Map<String, String> ids = orderService.createOrder(cartId, deliveryAddress, userId != null ? userId.toString() : null);

        if (!ids.isEmpty()) {
            String responseJson = "{\"newCartId\":\"" + ids.get("newCartId") + "\", \"orderId\":\"" + ids.get("orderId") + "\"}";
            return ResponseEntity.ok(responseJson);
        } else {
            return ResponseEntity.badRequest().body("Failed to create the order.");
        }
    }


    /**
     * Retrieves order history for the current logged-in user.
     *
     * @param userId The user ID from session.
     * @return A list of orders for the user.
     */
    @GetMapping("/order-history")
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

    /**
     * Updates the shipping status of an order.
     *
     * @param orderId   The ID of the order to update.
     * @param newStatus The new shipping status.
     * @return A response entity with success or error message.
     */
    @PostMapping("/update-shipping-status")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> updateShippingStatus(@RequestParam("orderId") Long orderId, @RequestParam("newStatus") String newStatus) {
        log.debug("Request to update shipping status for order with ID: {}", orderId);

        if (orderId != null && newStatus != null) {
            boolean statusUpdated = orderService.updateShippingStatus(orderId, newStatus);
            if (statusUpdated) {
                log.debug("Shipping status updated successfully for order with ID: {}", orderId);
                return ResponseEntity.ok("Shipping status updated successfully.");
            } else {
                log.error("Failed to update shipping status for order with ID: {}", orderId);
                return ResponseEntity.badRequest().body("Failed to update shipping status.");
            }
        } else {
            log.error("Invalid input for updating shipping status.");
            return ResponseEntity.badRequest().body("Invalid input for updating shipping status.");
        }
    }

    /**
     * Updates the tracking number of an order.
     *
     * @param orderId        The ID of the order to update.
     * @param trackingNumber The new tracking number.
     * @return A response entity with success or error message.
     */
    @PostMapping("/update-tracking-number")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> updateTrackingNumber(@RequestParam("orderId") Long orderId, @RequestParam("trackingNumber") Long trackingNumber) {
        log.debug("Request to update tracking number for order with ID: {}", orderId);

        if (orderId != null) {
            boolean statusUpdated = orderService.updateTrackingNumber(orderId, trackingNumber);
            if (statusUpdated) {
                log.debug("tracking number updated successfully for order with ID: {}", orderId);
                return ResponseEntity.ok("Shipping status updated successfully.");
            } else {
                log.error("Failed to update tracking number for order with ID: {}", orderId);
                return ResponseEntity.badRequest().body("Failed to update tracking number.");
            }
        } else {
            log.error("Invalid input for updating tracking number.");
            return ResponseEntity.badRequest().body("Invalid input for updating tracking number.");
        }
    }

    /**
     * Updates the user of an order if the username is currently null.
     *
     * @param orderId The ID of the order to update.
     * @param userId  The ID of the user to attach to the order.
     * @return A response entity with success or error message.
     */
    @PostMapping("/update-order-user-if-absent")
    @PreAuthorize("hasAnyRole('STAFF', 'CUSTOMER')")
    public ResponseEntity<?> updateOrderUserIfAbsent(@RequestParam("orderId") Long orderId, @RequestParam("userId") Long userId) {
        log.debug("Request to update user for order with ID: {}", orderId);

        if (orderId != null && userId != null) {
            boolean isUpdated = orderService.updateUserForOrderIfAbsent(orderId, userId);
            if (isUpdated) {
                log.debug("User updated successfully for order with ID: {}", orderId);
                return ResponseEntity.ok("User updated successfully for order.");
            } else {
                log.error("Failed to update user for order with ID: {}", orderId);
                return ResponseEntity.badRequest().body("Failed to update user for the order or user already present.");
            }
        } else {
            log.error("Invalid input for updating order user.");
            return ResponseEntity.badRequest().body("Invalid input for updating order user.");
        }
    }

}

/**
 * The DTO class for order request.
 */
class OrderRequest {
    private Long userId;
    private String cartId;
    private String deliveryAddress;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

}