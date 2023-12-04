package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.UserDao;
import com.elektrodevs.elektromart.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void userDetailsService_LoadUserByUsername_Success() {
        // Arrange
        String username = "testUser";
        User mockUser = new User(1L, username, "password", "email@test.com", 1L, "active", "ROLE_CUSTOMER");
        when(userDao.findByUsername(username)).thenReturn(Optional.of(mockUser));

        // Act
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertFalse(userDetails.getAuthorities().isEmpty());
        verify(userDao, times(1)).findByUsername(username);
    }

    @Test
    void userDetailsService_LoadUserByUsername_UserNotFound() {
        // Arrange
        String username = "nonexistentUser";
        when(userDao.findByUsername(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.userDetailsService().loadUserByUsername(username));
        verify(userDao, times(1)).findByUsername(username);
    }

    @Test
    void createUser_Success() {
        // Arrange
        User newUser = new User(1L, "testUser", "password", "email@test.com", 1L, "active", "ROLE_CUSTOMER");
        when(userDao.createUser(newUser)).thenReturn(newUser);

        // Act
        User createdUser = userService.createUser(newUser);

        // Assert
        assertNotNull(createdUser);
        assertEquals(newUser.getUsername(), createdUser.getUsername());
        verify(userDao, times(1)).createUser(newUser);
    }

    @Test
    void createUser_Failure() {
        // Arrange
        User newUser = new User(1L, "testUser", "password", "email@test.com", 1L, "active", "ROLE_CUSTOMER");
        when(userDao.createUser(newUser)).thenReturn(null);

        // Act
        User createdUser = userService.createUser(newUser);

        // Assert
        assertNull(createdUser);
        verify(userDao, times(1)).createUser(newUser);
    }
}
