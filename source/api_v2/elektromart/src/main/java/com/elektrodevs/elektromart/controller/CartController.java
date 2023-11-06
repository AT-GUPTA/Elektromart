package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.Product;
import com.elektrodevs.elektromart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/get-cart")
    public ResponseEntity<?> getCart(@RequestParam("cartId") String cartId) {
        Map<String, List<Product>> response = new HashMap<>();
        response.put("cartContent", cartService.getCartProducts(cartId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-product-quantity")
    public ResponseEntity<?> getCartProductQuantity(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug) {
        int quantity = cartService.getProductQuantity(cartId, productSlug);
        Map<String, Object> response = new HashMap<>();
        response.put("quantity", quantity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-product-to-cart")
    public ResponseEntity<?> addProductToCart(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug, @RequestParam("quantity") int quantity) {
        boolean result = cartService.addProductToCart(cartId, productSlug, quantity);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Product added to cart successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Couldn't add product to cart.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/create-cart")
    public ResponseEntity<?> createCart() {
        String cartId = cartService.createCart();
        Map<String, String> response = new HashMap<>();
        response.put("cartId", cartId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-quantity")
    public ResponseEntity<?> changeCartProductQuantity(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug, @RequestParam("quantity") int quantity) {
        cartService.changeItemQuantity(cartId, productSlug, quantity);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Quantity updated successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete-cart-product")
    public ResponseEntity<?> deleteProductFromCart(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug) {
        cartService.deleteFromCart(cartId, productSlug);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product removed from cart successfully.");
        return ResponseEntity.ok(response);
    }

}
