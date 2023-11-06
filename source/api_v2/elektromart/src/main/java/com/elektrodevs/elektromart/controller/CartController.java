package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.Product;
import com.elektrodevs.elektromart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for cart-related operations such as adding products to cart,
 * getting cart content, updating quantities, and creating carts.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    /**
     * Retrieves the content of a given cart.
     *
     * @param cartId The identifier of the cart.
     * @return A response entity with the cart content.
     */
    @GetMapping("/get-cart")
    public ResponseEntity<?> getCart(@RequestParam("cartId") String cartId) {
        log.debug("Fetching cart with id: {}", cartId);
        Map<String, List<Product>> response = new HashMap<>();
        response.put("cartContent", cartService.getCartProducts(cartId));
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves the quantity of a specific product in a given cart.
     *
     * @param cartId The identifier of the cart.
     * @param productSlug The slug of the product.
     * @return A response entity with the product quantity.
     */
    @GetMapping("/get-product-quantity")
    public ResponseEntity<?> getCartProductQuantity(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug) {
        log.debug("Fetching quantity for product {} in cart {}", productSlug, cartId);
        int quantity = cartService.getProductQuantity(cartId, productSlug);
        Map<String, Object> response = new HashMap<>();
        response.put("quantity", quantity);
        return ResponseEntity.ok(response);
    }

    /**
     * Adds a specified quantity of a product to the cart.
     *
     * @param cartId The identifier of the cart.
     * @param productSlug The slug of the product.
     * @param quantity The quantity to add.
     * @return A response entity with a success or error message.
     */
    @PostMapping("/add-product-to-cart")
    public ResponseEntity<?> addProductToCart(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug, @RequestParam("quantity") int quantity) {
        log.debug("Adding product {} to cart {} with quantity {}", productSlug, cartId, quantity);
        boolean result = cartService.addProductToCart(cartId, productSlug, quantity);
        Map<String, String> response = new HashMap<>();
        if (result) {
            log.debug("Product {} added to cart {} successfully.", productSlug, cartId);
            response.put("message", "Product added to cart successfully.");
            return ResponseEntity.ok(response);
        } else {
            log.error("Failed to add product {} to cart {}.", productSlug, cartId);
            response.put("error", "Couldn't add product to cart.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Creates a new cart and returns its identifier.
     *
     * @return A response entity with the new cart identifier.
     */
    @PostMapping("/create-cart")
    public ResponseEntity<?> createCart() {
        log.debug("Creating new cart");
        String cartId = cartService.createCart();
        Map<String, String> response = new HashMap<>();
        response.put("cartId", cartId);
        return ResponseEntity.ok(response);
    }

    /**
     * Changes the quantity of a specific product in the cart.
     *
     * @param cartId The identifier of the cart.
     * @param productSlug The slug of the product.
     * @param quantity The new quantity to set.
     * @return A response entity with a success message.
     */
    @PostMapping("/change-quantity")
    public ResponseEntity<?> changeCartProductQuantity(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug, @RequestParam("quantity") int quantity) {
        log.debug("Changing quantity for product {} in cart {} to {}", productSlug, cartId, quantity);
        cartService.changeItemQuantity(cartId, productSlug, quantity);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Quantity updated successfully.");
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a product from the cart.
     *
     * @param cartId The identifier of the cart.
     * @param productSlug The slug of the product to delete.
     * @return A response entity with a success message.
     */
    @PostMapping("/delete-cart-product")
    public ResponseEntity<?> deleteProductFromCart(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug) {
        log.debug("Deleting product {} from cart {}", productSlug, cartId);
        cartService.deleteFromCart(cartId, productSlug);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product removed from cart successfully.");
        return ResponseEntity.ok(response);
    }
}
