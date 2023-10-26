package com.elektromart.service;

import com.elektromart.dao.CartDao;
import com.elektromart.domain.Product;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;
import java.util.UUID;

public class CartService {

    private final CartDao cartDao = new CartDao();

    public Boolean addProductToCart(String cartId, String productSlug, int quantity) {
        return cartDao.addProductToCart(cartId, productSlug, quantity);
    }

    public JsonArray getCartProducts(String cartId) {

        List<Product> products = cartDao.getCartProducts(cartId);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Product product : products) {
            JsonObject productJson = Json.createObjectBuilder()
                    .add("id", product.getId())
                    .add("productSlug", product.getUrlSlug())
                    .add("name", product.getName())
                    .add("description", product.getDescription())
                    .add("price", product.getPrice())
                    .add("quantity", product.getQuantity())
                    .add("imageUrl", "/images/" + product.getUrlSlug() + ".jpg")
                    .build();

            arrayBuilder.add(productJson);
        }

        return arrayBuilder.build();
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
        return UUID.randomUUID().toString();
    }
}
