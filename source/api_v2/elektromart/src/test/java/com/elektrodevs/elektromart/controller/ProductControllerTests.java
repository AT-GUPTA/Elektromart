package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.Product;
import com.elektrodevs.elektromart.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        // Arrange
        List<Product> mockProducts = new ArrayList<>();
        when(productService.getProducts()).thenReturn(mockProducts);

        // Act
        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService).getProducts();
    }

    @Test
    void getAllFeaturedProducts_ShouldReturnListOfFeaturedProducts() {
        // Arrange
        List<Product> mockFeaturedProducts = new ArrayList<>();
        when(productService.getFeaturedProducts()).thenReturn(mockFeaturedProducts);

        // Act
        ResponseEntity<List<Product>> responseEntity = productController.getAllFeaturedProducts();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService).getFeaturedProducts();
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        // Arrange
        int productId = 1;
        Product mockProduct = new Product();
        when(productService.getProductById(productId)).thenReturn(mockProduct);

        // Act
        ResponseEntity<Product> responseEntity = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService).getProductById(productId);
    }

    @Test
    void getProductById_ShouldReturnNotFound_WhenProductDoesNotExist() {
        // Arrange
        int productId = 1;
        when(productService.getProductById(productId)).thenReturn(null);

        // Act
        ResponseEntity<Product> responseEntity = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
        verify(productService).getProductById(productId);
    }

    @Test
    void getProductBySlug_ShouldReturnProduct_WhenProductExists() {
        // Arrange
        String slug = "test-slug";
        Product mockProduct = new Product();
        when(productService.getProductBySlug(slug)).thenReturn(mockProduct);

        // Act
        ResponseEntity<Product> responseEntity = productController.getProductBySlug(slug);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService).getProductBySlug(slug);
    }

    @Test
    void getProductBySlug_ShouldReturnNotFound_WhenProductDoesNotExist() {
        // Arrange
        String slug = "test-slug";
        when(productService.getProductBySlug(slug)).thenReturn(null);

        // Act
        ResponseEntity<Product> responseEntity = productController.getProductBySlug(slug);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
        verify(productService).getProductBySlug(slug);
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct_WhenCreationIsSuccessful() {
        // Arrange
        Product newProduct = new Product();
        when(productService.createProduct(newProduct)).thenReturn(newProduct);

        // Act
        ResponseEntity<?> responseEntity = productController.createProduct(newProduct);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService).createProduct(newProduct);
    }

    @Test
    void createProduct_ShouldReturnBadRequest_WhenCreationFails() {
        // Arrange
        Product newProduct = new Product();
        when(productService.createProduct(newProduct)).thenReturn(null);

        // Act
        ResponseEntity<?> responseEntity = productController.createProduct(newProduct);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService).createProduct(newProduct);
    }

    @Test
    void downloadAllProducts_ShouldReturnJsonString() throws JsonProcessingException {
        // Arrange
        String mockJson = "{\"key\":\"value\"}";
        when(productService.getDownloadString()).thenReturn(mockJson);

        // Act
        ResponseEntity<?> responseEntity = productController.downloadAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService).getDownloadString();
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct_WhenUpdateIsSuccessful() {
        // Arrange
        int productId = 1;
        Product updatedProduct = new Product();
        when(productService.editProduct(updatedProduct)).thenReturn(updatedProduct);

        // Act
        ResponseEntity<?> responseEntity = productController.updateProduct(productId, updatedProduct);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService).editProduct(updatedProduct);
    }

    @Test
    void updateProduct_ShouldReturnBadRequest_WhenUpdateFails() {
        // Arrange
        int productId = 1;
        Product updatedProduct = new Product();
        when(productService.editProduct(updatedProduct)).thenReturn(null);

        // Act
        ResponseEntity<?> responseEntity = productController.updateProduct(productId, updatedProduct);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(productService).editProduct(updatedProduct);
    }
}
