package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.CartDao;
import com.elektrodevs.elektromart.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartDao cartDao;

    public Boolean addProductToCart(String cartId, String productSlug, int quantity) {
        Boolean result = cartDao.addProductToCart(cartId, productSlug, quantity);
        if (result) {
            log.debug("addProductToCart: Product with slug '{}' added to cart '{}' with quantity {}.", productSlug, cartId, quantity);
        } else {
            log.error("addProductToCart: Failed to add product with slug '{}' to cart '{}'.", productSlug, cartId);
        }
        return result;
    }

    public List<Product> getCartProducts(String cartId) {
        List<Product> products = cartDao.getCartProducts(cartId);
        log.debug("getCartProducts: Retrieved {} products from cart '{}'.", products.size(), cartId);
        return products;
    }

    public int getProductQuantity(String cartId, String productSlug) {
        int quantity = cartDao.getProductQuantity(cartId, productSlug);
        log.debug("getProductQuantity: Quantity of product with slug '{}' in cart '{}' is {}.", productSlug, cartId, quantity);
        return quantity;
    }

    public Boolean changeItemQuantity(String cartId, String productSlug, int quantity) {
        Boolean result = cartDao.changeItemQuantity(cartId, productSlug, quantity);
        if (result) {
            log.debug("changeItemQuantity: Quantity of product with slug '{}' in cart '{}' changed to {}.", productSlug, cartId, quantity);
        } else {
            log.error("changeItemQuantity: Failed to change quantity of product with slug '{}' in cart '{}' to {}.", productSlug, cartId, quantity);
        }
        return result;
    }

    public Boolean deleteFromCart(String cartId, String productSlug) {
        Boolean result = cartDao.deleteFromCart(cartId, productSlug);
        if (result) {
            log.debug("deleteFromCart: Product with slug '{}' removed from cart '{}'.", productSlug, cartId);
        } else {
            log.error("deleteFromCart: Failed to remove product with slug '{}' from cart '{}'.", productSlug, cartId);
        }
        return result;
    }

    public String createCart() {
        String cartId = UUID.randomUUID().toString();
        cartDao.createCart(cartId);
        log.debug("createCart: Created a new cart with ID '{}'.", cartId);
        return cartId;
    }
    public Boolean clearCart(String cartId) {
        Boolean result = cartDao.clearCart(cartId);
        if (result) {
            log.debug("clearCart: Cart with ID '{}' has been cleared.", cartId);
        } else {
            log.error("clearCart: Failed to clear cart with ID '{}'.", cartId);
        }
        return result;
    }
}
