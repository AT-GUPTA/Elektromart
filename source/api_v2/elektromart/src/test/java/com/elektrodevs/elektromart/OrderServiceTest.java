package com.elektrodevs.elektromart;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elektrodevs.elektromart.dao.OrderDao;
import com.elektrodevs.elektromart.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Test class for OrderService specifically for SetOrderOwner
 */
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderDao orderDao;
    @InjectMocks
    private OrderService orderService;

    /**
     * Test to ensure that updateUserForOrderIfAbsent returns true when the update is successful.
     */
    @Test
    public void updateUserForOrderIfAbsent_ShouldReturnTrue_WhenUpdateIsSuccessful() {
        // Arrange
        Long orderId = 1L;
        Long userId = 1L;
        when(orderDao.updateUserForOrderIfAbsent(orderId, userId)).thenReturn(true);

        // Act
        boolean result = orderService.updateUserForOrderIfAbsent(orderId, userId);

        // Assert
        assertTrue(result);
        verify(orderDao).updateUserForOrderIfAbsent(orderId, userId);
    }

    /**
     * Test to ensure that updateUserForOrderIfAbsent returns false when the update fails.
     */
    @Test
    public void updateUserForOrderIfAbsent_ShouldReturnFalse_WhenUpdateFails() {
        // Arrange
        Long orderId = 1L;
        Long userId = 1L;
        when(orderDao.updateUserForOrderIfAbsent(orderId, userId)).thenReturn(false);

        // Act
        boolean result = orderService.updateUserForOrderIfAbsent(orderId, userId);

        // Assert
        assertFalse(result);
        verify(orderDao).updateUserForOrderIfAbsent(orderId, userId);
    }

    /**
     * Test to verify that updateUserForOrderIfAbsent throws DataIntegrityViolationException when a data access issue occurs.
     */
    @Test
    public void updateUserForOrderIfAbsent_ShouldThrowException_WhenDataAccessExceptionOccurs() {
        // Arrange
        Long orderId = 1L;
        Long userId = 1L;
        when(orderDao.updateUserForOrderIfAbsent(orderId, userId))
                .thenThrow(new DataIntegrityViolationException("..."));

        // Act and Assert
        assertThrows(DataIntegrityViolationException.class, () ->
                orderService.updateUserForOrderIfAbsent(orderId, userId));
    }

    /**
     * Test to ensure that updateUserForOrderIfAbsent does not perform an update when the orderId is null.
     */
    @Test
    public void updateUserForOrderIfAbsent_ShouldNotUpdate_WhenOrderIdIsNull() {
        // Arrange
        Long orderId = null;
        Long userId = 1L;

        // Act
        boolean result = orderService.updateUserForOrderIfAbsent(orderId, userId);

        // Assert
        assertFalse(result);
        verify(orderDao, never()).updateUserForOrderIfAbsent(anyLong(), anyLong());
    }

    /**
     * Test to ensure that updateUserForOrderIfAbsent does not perform an update when the userId is null.
     */
    @Test
    public void updateUserForOrderIfAbsent_ShouldNotUpdate_WhenUserIdIsNull() {
        // Arrange
        Long orderId = 1L;
        Long userId = null;

        // Act
        boolean result = orderService.updateUserForOrderIfAbsent(orderId, userId);

        // Assert
        assertFalse(result);
        verify(orderDao, never()).updateUserForOrderIfAbsent(anyLong(), anyLong());
    }
}
