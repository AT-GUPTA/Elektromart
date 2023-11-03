package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/get-cart")
    public ResponseEntity<?> getCart(@RequestParam("cartId") String cartId) {
        return ResponseEntity.ok(cartService.getCartProducts(cartId));
    }

    @GetMapping("/get-product-quantity")
    public ResponseEntity<?> getCartProductQuantity(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug) {
        int quantity = cartService.getProductQuantity(cartId, productSlug);
        return ResponseEntity.ok(quantity);
    }

    @PostMapping("/add-product-to-cart")
    public ResponseEntity<?> addProductToCart(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug, @RequestParam("quantity") int quantity) {
        boolean result = cartService.addProductToCart(cartId, productSlug, quantity);
        return result ? ResponseEntity.ok("Product added to cart successfully.") : ResponseEntity.badRequest().body("Couldn't add product to cart.");
    }

    @PostMapping("/create-cart")
    public ResponseEntity<?> createCart() {
        String cartId = cartService.createCart();
        return ResponseEntity.ok(cartId);
    }

    @PostMapping("/change-quantity")
    public ResponseEntity<?> changeCartProductQuantity(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug, @RequestParam("quantity") int quantity) {
        cartService.changeItemQuantity(cartId, productSlug, quantity);
        return ResponseEntity.ok("Quantity updated successfully.");
    }

    @PostMapping("/delete-cart-product")
    public ResponseEntity<?> deleteProductFromCart(@RequestParam("cartId") String cartId, @RequestParam("productSlug") String productSlug) {
        cartService.deleteFromCart(cartId, productSlug);
        return ResponseEntity.ok("Product removed from cart successfully.");
    }
}
