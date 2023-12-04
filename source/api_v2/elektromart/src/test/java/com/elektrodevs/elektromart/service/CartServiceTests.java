package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.CartDao;
import com.elektrodevs.elektromart.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartService cartService;

    @Test
    void addProductToCart_SuccessfulAddition_ReturnsTrue() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        String productSlug = "product-slug";
        int quantity = 2;

        when(cartDao.addProductToCart(anyString(), anyString(), anyInt())).thenReturn(true);

        // Act
        Boolean result = cartService.addProductToCart(cartId, productSlug, quantity);

        // Assert
        assertTrue(result);
        verify(cartDao, times(1)).addProductToCart(cartId, productSlug, quantity);
    }

    @Test
    void addProductToCart_FailedAddition_ReturnsFalse() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        String productSlug = "product-slug";
        int quantity = 2;

        when(cartDao.addProductToCart(anyString(), anyString(), anyInt())).thenReturn(false);

        // Act
        Boolean result = cartService.addProductToCart(cartId, productSlug, quantity);

        // Assert
        assertFalse(result);
        verify(cartDao, times(1)).addProductToCart(cartId, productSlug, quantity);
    }

    @Test
    void getCartProducts_ReturnsListOfProducts() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        List<Product> expectedProducts = new ArrayList<>();

        when(cartDao.getCartProducts(anyString())).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = cartService.getCartProducts(cartId);

        // Assert
        assertEquals(expectedProducts, actualProducts);
        verify(cartDao, times(1)).getCartProducts(cartId);
    }

    @Test
    void getProductQuantity_ReturnsQuantity() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        String productSlug = "product-slug";
        int expectedQuantity = 3;

        when(cartDao.getProductQuantity(anyString(), anyString())).thenReturn(expectedQuantity);

        // Act
        int actualQuantity = cartService.getProductQuantity(cartId, productSlug);

        // Assert
        assertEquals(expectedQuantity, actualQuantity);
        verify(cartDao, times(1)).getProductQuantity(cartId, productSlug);
    }

    @Test
    void changeItemQuantity_SuccessfulChange_ReturnsTrue() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        String productSlug = "product-slug";
        int quantity = 5;

        when(cartDao.changeItemQuantity(anyString(), anyString(), anyInt())).thenReturn(true);

        // Act
        Boolean result = cartService.changeItemQuantity(cartId, productSlug, quantity);

        // Assert
        assertTrue(result);
        verify(cartDao, times(1)).changeItemQuantity(cartId, productSlug, quantity);
    }

    @Test
    void changeItemQuantity_FailedChange_ReturnsFalse() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        String productSlug = "product-slug";
        int quantity = 5;

        when(cartDao.changeItemQuantity(anyString(), anyString(), anyInt())).thenReturn(false);

        // Act
        Boolean result = cartService.changeItemQuantity(cartId, productSlug, quantity);

        // Assert
        assertFalse(result);
        verify(cartDao, times(1)).changeItemQuantity(cartId, productSlug, quantity);
    }

    @Test
    void deleteFromCart_SuccessfulDeletion_ReturnsTrue() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        String productSlug = "product-slug";

        when(cartDao.deleteFromCart(anyString(), anyString())).thenReturn(true);

        // Act
        Boolean result = cartService.deleteFromCart(cartId, productSlug);

        // Assert
        assertTrue(result);
        verify(cartDao, times(1)).deleteFromCart(cartId, productSlug);
    }

    @Test
    void deleteFromCart_FailedDeletion_ReturnsFalse() {
        // Arrange
        String cartId = UUID.randomUUID().toString();
        String productSlug = "product-slug";

        when(cartDao.deleteFromCart(anyString(), anyString())).thenReturn(false);

        // Act
        Boolean result = cartService.deleteFromCart(cartId, productSlug);

        // Assert
        assertFalse(result);
        verify(cartDao, times(1)).deleteFromCart(cartId, productSlug);
    }

    @Test
    void createCart_ReturnsNewCartId() {
        // Act
        String actualCartId = cartService.createCart();

        // Assert
        assertNotNull(actualCartId);
        verify(cartDao, times(1)).createCart(actualCartId);
    }

    @Test
    void clearCart_SuccessfulClear_ReturnsTrue() {
        // Arrange
        String cartId = UUID.randomUUID().toString();

        when(cartDao.clearCart(anyString())).thenReturn(true);

        // Act
        Boolean result = cartService.clearCart(cartId);

        // Assert
        assertTrue(result);
        verify(cartDao, times(1)).clearCart(cartId);
    }

    @Test
    void clearCart_FailedClear_ReturnsFalse() {
        // Arrange
        String cartId = UUID.randomUUID().toString();

        when(cartDao.clearCart(anyString())).thenReturn(false);

        // Act
        Boolean result = cartService.clearCart(cartId);

        // Assert
        assertFalse(result);
        verify(cartDao, times(1)).clearCart(cartId);
    }
}
