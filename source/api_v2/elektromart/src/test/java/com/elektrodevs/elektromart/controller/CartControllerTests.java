package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.Product;
import com.elektrodevs.elektromart.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartControllerTests {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Test
    void getCart_ShouldReturnCartContent() {
        // Arrange
        String cartId = "testCartId";
        List<Product> mockCartContent = new ArrayList<>();
        when(cartService.getCartProducts(cartId)).thenReturn(mockCartContent);

        // Act
        ResponseEntity<?> responseEntity = cartController.getCart(cartId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(cartService).getCartProducts(cartId);
    }

    @Test
    void getCartProductQuantity_ShouldReturnProductQuantity() {
        // Arrange
        String cartId = "testCartId";
        String productSlug = "testProductSlug";
        int mockQuantity = 5;
        when(cartService.getProductQuantity(cartId, productSlug)).thenReturn(mockQuantity);

        // Act
        ResponseEntity<?> responseEntity = cartController.getCartProductQuantity(cartId, productSlug);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(cartService).getProductQuantity(cartId, productSlug);
    }

    @Test
    void addProductToCart_ShouldReturnSuccessMessage_WhenAdditionIsSuccessful() {
        // Arrange
        String cartId = "testCartId";
        String productSlug = "testProductSlug";
        int quantity = 2;
        when(cartService.addProductToCart(cartId, productSlug, quantity)).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = cartController.addProductToCart(cartId, productSlug, quantity);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(cartService).addProductToCart(cartId, productSlug, quantity);
    }

    @Test
    void addProductToCart_ShouldReturnErrorMessage_WhenAdditionFails() {
        // Arrange
        String cartId = "testCartId";
        String productSlug = "testProductSlug";
        int quantity = 2;
        when(cartService.addProductToCart(cartId, productSlug, quantity)).thenReturn(false);

        // Act
        ResponseEntity<?> responseEntity = cartController.addProductToCart(cartId, productSlug, quantity);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(cartService).addProductToCart(cartId, productSlug, quantity);
    }

    @Test
    void createCart_ShouldReturnCartId() {
        // Arrange
        String mockCartId = "testCartId";
        when(cartService.createCart()).thenReturn(mockCartId);

        // Act
        ResponseEntity<?> responseEntity = cartController.createCart();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(cartService).createCart();
    }

    @Test
    void changeCartProductQuantity_ShouldReturnSuccessMessage() {
        // Arrange
        String cartId = "testCartId";
        String productSlug = "testProductSlug";
        int quantity = 3;

        // Act
        ResponseEntity<?> responseEntity = cartController.changeCartProductQuantity(cartId, productSlug, quantity);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(cartService).changeItemQuantity(cartId, productSlug, quantity);
    }

    @Test
    void deleteProductFromCart_ShouldReturnSuccessMessage() {
        // Arrange
        String cartId = "testCartId";
        String productSlug = "testProductSlug";

        // Act
        ResponseEntity<?> responseEntity = cartController.deleteProductFromCart(cartId, productSlug);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(cartService).deleteFromCart(cartId, productSlug);
    }

    @Test
    void clearCart_ShouldReturnSuccessMessage_WhenCartIsCleared() {
        // Arrange
        String cartId = "testCartId";
        when(cartService.clearCart(cartId)).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = cartController.clearCart(cartId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(cartService).clearCart(cartId);
    }

    @Test
    void clearCart_ShouldReturnErrorMessage_WhenCartClearingFails() {
        // Arrange
        String cartId = "testCartId";
        when(cartService.clearCart(cartId)).thenReturn(false);

        // Act
        ResponseEntity<?> responseEntity = cartController.clearCart(cartId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(cartService).clearCart(cartId);
    }
}
