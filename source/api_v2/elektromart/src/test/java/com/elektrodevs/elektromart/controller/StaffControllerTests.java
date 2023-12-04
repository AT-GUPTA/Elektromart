package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.User;
import com.elektrodevs.elektromart.service.UserService;
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
public class StaffControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private StaffController staffController;

    @Test
    void getAllStaff_ShouldReturnListOfStaffMembers() {
        // Arrange
        List<User> mockStaffMembers = new ArrayList<>();
        when(userService.getAllStaffMembers()).thenReturn(mockStaffMembers);

        // Act
        ResponseEntity<List<User>> responseEntity = staffController.getAllStaff();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(userService).getAllStaffMembers();
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() {
        // Arrange
        List<User> mockCustomers = new ArrayList<>();
        when(userService.getAllCustomers()).thenReturn(mockCustomers);

        // Act
        ResponseEntity<List<User>> responseEntity = staffController.getAllCustomers();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(userService).getAllCustomers();
    }

    @Test
    void grantStaffPrivileges_ShouldReturnOkResponse() {
        // Arrange
        String username = "testUsername";

        // Act
        ResponseEntity<?> responseEntity = staffController.grantStaffPrivileges(username);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService).grantStaffPrivileges(username);
    }

    @Test
    void revokeStaffPrivileges_ShouldReturnOkResponse() {
        // Arrange
        String username = "testUsername";

        // Act
        ResponseEntity<?> responseEntity = staffController.revokeStaffPrivileges(username);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService).revokeStaffPrivileges(username);
    }
}
