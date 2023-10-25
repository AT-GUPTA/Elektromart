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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();

        try {
            if ("/".equals(path) || path == null) {
                // GET /products
                List<Product> products = productService.getProducts();
                String productsJson = objectMapper.writeValueAsString(products);
                resp.setContentType("application/json");
                resp.getWriter().write(productsJson);
            } else if (path.matches("/\\d+")) {
                // GET /products/{id}
                int productId = Integer.parseInt(path.substring(1));
                Product product = productService.getProductById(productId);

                if (product != null) {
                    String productJson = objectMapper.writeValueAsString(product);
                    resp.setContentType("application/json");
                    resp.getWriter().write(productJson);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } else if ("/download".equals(path)) {
                // GET /products/download
                List<Product> products = productService.getProducts();
                String json = objectMapper.writeValueAsString(products);

                resp.setContentType("application/json");
                resp.setHeader("Content-Disposition", "attachment; filename=products.json"); // Specify the desired filename
                resp.getWriter().write(json);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("An error occurred while processing the request");
            Logger.error("An error occurred while processing the request: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String path = req.getPathInfo();

            if ("/".equals(path) ||path == null) {
                // POST /products for creating a new product
                Product newProduct = objectMapper.readValue(req.getInputStream(), Product.class);
                Product createdProduct = productService.createProduct(newProduct);

                if (createdProduct != null) {
                    resp.setStatus(HttpServletResponse.SC_CREATED); // HTTP 201 Created
                    String productJson = objectMapper.writeValueAsString(createdProduct);
                    resp.setContentType("application/json");
                    resp.getWriter().write(productJson);
                } else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create a new product");
                }
            } else if (path.matches("/\\d+")) {
                // POST /products/{id} for updating an existing product
                int productId = Integer.parseInt(path.substring(1));
                Product existingProduct = productService.getProductById(productId);

                if (existingProduct != null) {
                    Product updatedProduct = objectMapper.readValue(req.getInputStream(), Product.class);
                    updatedProduct.setId(productId);
                    Product result = productService.editProduct(updatedProduct);

                    if (result != null) {
                        resp.setStatus(HttpServletResponse.SC_OK);
                        String productJson = objectMapper.writeValueAsString(result);
                        resp.setContentType("application/json");
                        resp.getWriter().write(productJson);
                    } else {
                        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update product");
                    }
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("An error occurred while processing the request");
            Logger.error("An error occurred while processing the request: " + e.getMessage());
        }
    }
}
