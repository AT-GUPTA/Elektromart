package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.ProductDao;
import com.elektrodevs.elektromart.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTests {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProducts() {
        // Arrange
        List<Product> mockProducts = Arrays.asList(new Product(), new Product());
        when(productDao.getProducts()).thenReturn(mockProducts);

        // Act
        List<Product> products = productService.getProducts();

        // Assert
        assertNotNull(products);
        assertEquals(mockProducts.size(), products.size());
        verify(productDao, times(1)).getProducts();
    }

    @Test
    void getFeaturedProducts() {
        // Arrange
        List<Product> mockProducts = Arrays.asList(new Product(), new Product());
        when(productDao.getFeaturedProducts()).thenReturn(mockProducts);

        // Act
        List<Product> featuredProducts = productService.getFeaturedProducts();

        // Assert
        assertNotNull(featuredProducts);
        assertEquals(mockProducts.size(), featuredProducts.size());
        verify(productDao, times(1)).getFeaturedProducts();
    }

    @Test
    void createProduct_Success() {
        // Arrange
        Product newProduct = new Product();
        when(productDao.createProduct(newProduct)).thenReturn(newProduct);

        // Act
        Product createdProduct = productService.createProduct(newProduct);

        // Assert
        assertNotNull(createdProduct);
        verify(productDao, times(1)).createProduct(newProduct);
    }

    @Test
    void createProduct_Failure() {
        // Arrange
        Product newProduct = new Product();
        when(productDao.createProduct(newProduct)).thenReturn(null);

        // Act
        Product createdProduct = productService.createProduct(newProduct);

        // Assert
        assertNull(createdProduct);
        verify(productDao, times(1)).createProduct(newProduct);
    }
}
