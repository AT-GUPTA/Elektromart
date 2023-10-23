package com.elektromart.service;

import com.elektromart.dao.CartDao;
import com.elektromart.domain.Cart;

public class CartService {

    private final CartDao cartDao = new CartDao();

    public Cart getCartById(String cartId) {
        return cartDao.getCartById(cartId);
    }

    // todo Add methods for changeItemQuantity and delete-from-cart
}
