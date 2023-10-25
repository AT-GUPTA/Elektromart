package com.elektromart.controller;

import com.elektromart.domain.Cart;
import com.elektromart.service.CartService;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart/*")
public class CartController extends HttpServlet {

    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();

        if ("/getCart".equals(path)) {
            String cartId = req.getParameter("cartId");
            Cart cart = cartService.getCartById(cartId);
            resp.setContentType("application/json");
            JsonArray cartContent = cartService.getCartContent(cartId);
            JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("status", "SUCCESS")
                    .add("message", "Cart fetched successfully.")
                    .add("cartContent", cartContent)
                    .build();
            resp.getWriter().write(jsonResponse.toString());
        }
    }
}
