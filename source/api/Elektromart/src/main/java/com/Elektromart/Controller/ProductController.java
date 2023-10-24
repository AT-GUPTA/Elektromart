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

        if ("/download".equals(path)) {
            try {
                List<Product> products = productService.getProducts();
                String json = objectMapper.writeValueAsString(products);

                resp.setContentType("application/json");
                resp.setHeader("Content-Disposition", "attachment; filename=products.json"); // Specify the desired filename
                resp.getWriter().write(json);
            } catch (Exception e) {
                Logger.error("An error occurred while processing the request GET /products/download. Message : " + e.getMessage());

                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("An error occurred while processing the request");
            }
        }
    }
}
