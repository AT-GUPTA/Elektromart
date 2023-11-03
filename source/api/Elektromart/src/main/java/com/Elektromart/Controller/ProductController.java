package com.elektromart.controller;

import com.elektromart.domain.Product;
import com.elektromart.service.ProductService;
import com.elektromart.utilis.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/products/*")
public class ProductController extends HttpServlet {
    private final ProductService productService = new ProductService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String path = req.getPathInfo();

            if ("/".equals(path) || path == null) {
                handleGetAllProducts(resp);
            } else if (path.matches("/\\d+")) {
                handleGetProductById(path, resp);
            } else if ("/download".equals(path)) {
                handleDownloadProducts(resp);
            }  else if (path.matches("/.+")) {
                handleGetProductBySlug(path, resp);
            } else {
                sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            handleException(resp, e);
        }
    }

    private void handleGetAllProducts(HttpServletResponse resp) throws IOException {
        List<Product> products = productService.getProducts();
        sendResponse(resp, products);
    }

    private void handleGetProductById(String path, HttpServletResponse resp) throws IOException {
        int productId = Integer.parseInt(path.substring(1));
        Product product = productService.getProductById(productId);
        if (product != null) {
            sendResponse(resp, product);
        } else {
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }
    }

    private void handleGetProductBySlug(String path, HttpServletResponse resp) throws IOException {
        String urlSlug = path.substring(1);
        List<Product> products = productService.getProducts();
        Product product = products.stream()
                .filter(e -> e.getUrlSlug().equalsIgnoreCase(urlSlug))
                .findFirst()
                .orElse(null);
        if (product != null) {
            sendResponse(resp, product);
        } else {
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }
    }

    private void handleDownloadProducts(HttpServletResponse resp) throws IOException {
        List<Product> products = productService.getProducts();
        String json = objectMapper.writeValueAsString(products);

        resp.setContentType("application/json");
        resp.setHeader("Content-Disposition", "attachment; filename=products.json");
        resp.getWriter().write(json);
    }

    private void sendResponse(HttpServletResponse resp, Object data) throws IOException {
        String jsonResponse = objectMapper.writeValueAsString(data);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonResponse);
    }

    private void sendError(HttpServletResponse resp, int statusCode, String message) throws IOException {
        resp.sendError(statusCode, message);
    }

    private void handleException(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().write("An error occurred while processing the request");
        Logger.error("An error occurred while processing the request: " + e.getMessage());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String path = req.getPathInfo();

            if ("/".equals(path) || path == null) {
                handleCreateProduct(req, resp);
            } else if (path.matches("/\\d+")) {
                handleUpdateProduct(path, req, resp);
            } else {
                sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            handleException(resp, e);
        }
    }

    private void handleCreateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Product newProduct = objectMapper.readValue(req.getInputStream(), Product.class);
        Product createdProduct = productService.createProduct(newProduct);

        if (createdProduct != null) {
            sendResponse(resp, createdProduct, HttpServletResponse.SC_CREATED);
        } else {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create a new product");
        }
    }

    private void handleUpdateProduct(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int productId = Integer.parseInt(path.substring(1));
        Product existingProduct = productService.getProductById(productId);

        if (existingProduct != null) {
            Product updatedProduct = objectMapper.readValue(req.getInputStream(), Product.class);
            updatedProduct.setId(productId);
            Product result = productService.editProduct(updatedProduct);

            if (result != null) {
                sendResponse(resp, result, HttpServletResponse.SC_OK);
            } else {
                sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update product");
            }
        } else {
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }
    }

    private void sendResponse(HttpServletResponse resp, Object data, int statusCode) throws IOException {
        String jsonResponse = objectMapper.writeValueAsString(data);
        resp.setContentType("application/json");
        resp.setStatus(statusCode); // Set the status code
        resp.getWriter().write(jsonResponse);
    }
}
