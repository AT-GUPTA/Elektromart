package com.elektromart.service;

import com.elektromart.dao.CartDao;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.sql.ResultSet;
import java.util.UUID;

public class CartService {

    private final CartDao cartDao = new CartDao();

    public JsonArray getCartProducts(String cartId) {
        ResultSet rs = cartDao.getCartProducts(cartId);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        try {
            while (rs.next()) {
                JsonObject product = Json.createObjectBuilder()
                        .add("id", rs.getInt("id"))
                        .add("productSlug", rs.getString("url_slug"))
                        .add("name", rs.getString("name"))
                        .add("description", rs.getString("description"))
                        .add("price", rs.getDouble("price"))
                        .add("quantity", rs.getInt("quantity"))
                        .add("imageUrl", "/images/" + rs.getString("url_slug") + ".jpg")
                        .build();

                arrayBuilder.add(product);
            }
            return arrayBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
            return Json.createArrayBuilder().build();
        }
    }

    public Boolean changeItemQuantity(String cartId, String productSlug, int quantity) {
        return changeItemQuantity(cartId, productSlug, quantity);
    }

    public Boolean deleteFromCart(String cartId, String productSlug) {
        return cartDao.deleteFromCart(cartId, productSlug);
    }

    public String createCart() {
        return UUID.randomUUID().toString();
    }
}
