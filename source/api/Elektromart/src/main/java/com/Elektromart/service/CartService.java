package com.elektromart.service;

import com.elektromart.dao.CartDao;
import com.elektromart.domain.Cart;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

public class CartService {

    private final CartDao cartDao = new CartDao();

    public Cart getCartById(String cartId) {
        return cartDao.getCartById(cartId);
    }

    public JsonArray getCartContent(String cartId) {
        JsonObject iphone = Json.createObjectBuilder()
                .add("id", 1)
                .add("name", "iPhone 13 Pro")
                .add("description", "The iPhone 13 Pro is Apple's latest flagship smartphone, featuring a stunning Super Retina XDR display, A15 Bionic chip, and advanced camera system.")
                .add("price", 999.99)
                .add("quantity", 1)
                .add("imageUrl", "/images/iphone.jpg")
                .build();

        // Samsung Galaxy S21 data
        JsonObject samsung = Json.createObjectBuilder()
                .add("id", 2)
                .add("name", "Samsung Galaxy S21")
                .add("description", "The Samsung Galaxy S21 offers a sleek design, high-quality display, and a powerful processor for a smooth user experience.")
                .add("price", 799.99)
                .add("quantity", 2)
                .add("imageUrl", "/images/galaxy.jpg")
                .build();

        // Create JsonArray and add the above objects
        return Json.createArrayBuilder()
                .add(iphone)
                .add(samsung)
                .build();
    }

    // todo Add methods for changeItemQuantity and delete-from-cart
}
