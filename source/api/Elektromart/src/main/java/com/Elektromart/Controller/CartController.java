package com.elektromart.controller;

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
        switch (path) {
            case "/get-cart":
                getCart(req, resp);
                break;
            case "/create-cart":
                createCart(resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        switch (path) {
            case "/change-quantity" -> changeCartProductQuantity(req, resp);
            case "/delete-cart-product" -> deleteProductFromCart(req, resp);
            default -> {
            }
        }
    }

    private void getCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cartId = req.getParameter("cartId");
        resp.setContentType("application/json");
        JsonArray cartProducts = cartService.getCartProducts(cartId);
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("status", "SUCCESS")
                .add("message", "Cart fetched successfully.")
                .add("cartContent", cartProducts)
                .build();
        writeResponse(resp, jsonResponse);
    }

    private void createCart(HttpServletResponse resp) throws IOException {
        String cartId = cartService.createCart();
        resp.setContentType("application/json");
        JsonObject jsonResponse = Json.createObjectBuilder()
                .add("status", "SUCCESS")
                .add("message", "Cart created successfully.")
                .add("cartId", cartId)
                .build();
        writeResponse(resp, jsonResponse);
    }

    private void changeCartProductQuantity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cartId = req.getParameter("cartId");
        String productSlug = req.getParameter("productSlug");
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        cartService.changeItemQuantity(cartId, productSlug, quantity);
        resp.setContentType("application/json");
        JsonObject jsonResponse = buildJsonResponse("SUCCESS", "Quantity updated successfully.");
        writeResponse(resp, jsonResponse);
    }

    private void deleteProductFromCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cartId = req.getParameter("cartId");
        String productSlug = req.getParameter("productSlug");

        cartService.deleteFromCart(cartId, productSlug);
        resp.setContentType("application/json");
        JsonObject jsonResponse = buildJsonResponse("SUCCESS", "Product removed from cart successfully.");
        writeResponse(resp, jsonResponse);
    }

    private JsonObject buildJsonResponse(String status, String message) {
        return Json.createObjectBuilder()
                .add("status", status)
                .add("message", message)
                .build();
    }

    private void writeResponse(HttpServletResponse resp, JsonObject jsonResponse) throws IOException {
        resp.getWriter().write(jsonResponse.toString());
    }
}
