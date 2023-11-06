package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.Product;
import com.elektrodevs.elektromart.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for product-related operations.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class ProductController {

    private final ProductService productService;

    /**
     * Retrieves all products.
     *
     * @return A list of products.
     */
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.debug("Request to get all products");
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product.
     * @return The product if found, or a 404 not found status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        log.debug("Request to get product by id: {}", id);
        Product product = productService.getProductById(id);
        return (product != null) ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    /**
     * Retrieves a product by its URL slug.
     *
     * @param urlSlug The URL slug of the product.
     * @return The product if found, or a 404 not found status.
     */
    @GetMapping("/slug/{urlSlug}")
    public ResponseEntity<Product> getProductBySlug(@PathVariable String urlSlug) {
        log.debug("Request to get product by slug: {}", urlSlug);
        Product product = productService.getProductBySlug(urlSlug);
        return (product != null) ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    /**
     * Creates a new product.
     *
     * @param newProduct The product to create.
     * @return The created product, or an error message if creation failed.
     */
    @PostMapping("/")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> createProduct(@RequestBody Product newProduct) {
        log.debug("Request to create a new product");
        Product product = productService.createProduct(newProduct);
        return (product != null)
                ? ResponseEntity.ok(product)
                : ResponseEntity.badRequest().body("Error creating product");
    }

    /**
     * Downloads all products as a JSON file.
     *
     * @return A JSON file containing all products.
     * @throws JsonProcessingException If an error occurs during JSON processing.
     */
    @PostMapping("/download")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> downloadAllProducts() throws JsonProcessingException {
        log.debug("Request to download all products");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products = productService.getProducts();
        String json = objectMapper.writeValueAsString(products);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", "products.json");

        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * Updates an existing product.
     *
     * @param id            The ID of the product to update.
     * @param updatedProduct The updated product data.
     * @return The updated product, or an error message if the update failed.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        log.debug("Request to update product with id: {}", id);
        updatedProduct.setId(id);
        Product product = productService.editProduct(updatedProduct);
        return (product != null)
                ? ResponseEntity.ok(product)
                : ResponseEntity.badRequest().body("Error updating product");
    }

}
