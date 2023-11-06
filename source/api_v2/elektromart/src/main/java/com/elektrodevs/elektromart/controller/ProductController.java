package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.Product;
import com.elektrodevs.elektromart.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin (origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getProducts();
        return  ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/slug/{urlSlug}")
    public ResponseEntity<Product> getProductById(@PathVariable String urlSlug) {
        List<Product> products = productService.getProducts();
        Product product = products.stream().filter(e -> e.getUrlSlug().equalsIgnoreCase(urlSlug)).toList().get(0);

        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> createProduct(@RequestBody Product newProduct) {
        Product product = productService.createProduct(newProduct);
        return (product != null)
                ? ResponseEntity.ok(product)
                : ResponseEntity.badRequest().body("Error");
    }

    @PostMapping("/download")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> downloadAllProducts() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products = productService.getProducts();
        String json = objectMapper.writeValueAsString(products);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", "products.json");

        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        updatedProduct.setId(id);
        Product product = productService.editProduct(updatedProduct);
        return (product != null)
                ? ResponseEntity.ok(product)
                : ResponseEntity.badRequest().body("Error can't update");
    }

}
