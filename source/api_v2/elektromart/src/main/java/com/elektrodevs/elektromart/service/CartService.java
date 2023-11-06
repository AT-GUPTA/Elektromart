package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.CartDao;
import com.elektrodevs.elektromart.domain.Product;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartDao cartDao;

    public Boolean addProductToCart(String cartId, String productSlug, int quantity) {
        return cartDao.addProductToCart(cartId, productSlug, quantity);
    }

    public List<Product> getCartProducts(String cartId) {
        List<Product> products = cartDao.getCartProducts(cartId);

        return products;
    }

    public int getProductQuantity(String cartId, String productSlug) {
        return cartDao.getProductQuantity(cartId, productSlug);
    }

    public Boolean changeItemQuantity(String cartId, String productSlug, int quantity) {
        return cartDao.changeItemQuantity(cartId, productSlug, quantity);
    }

    public Boolean deleteFromCart(String cartId, String productSlug) {
        return cartDao.deleteFromCart(cartId, productSlug);
    }

    public String createCart() {
        String cartId =  UUID.randomUUID().toString();
        cartDao.createCart(cartId);
        return cartId;
    }
}
