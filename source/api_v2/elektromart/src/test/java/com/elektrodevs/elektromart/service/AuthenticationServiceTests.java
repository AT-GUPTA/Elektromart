package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.UserDao;
import com.elektrodevs.elektromart.domain.User;
import com.elektrodevs.elektromart.dto.JwtAuthenticationResponse;
import com.elektrodevs.elektromart.dto.SignInRequest;
import com.elektrodevs.elektromart.dto.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {
    @Mock
    private UserDao userDao;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void signup_ValidRequest_ReturnsJwtAuthenticationResponse() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest("passcode", 1L, "email@test.com", "cartId");
        User user = User.builder().username("username").email("email").password("encodedPassword").roleId(1L).cartId("cartId").build();
        JwtAuthenticationResponse expectedResponse = JwtAuthenticationResponse.builder().token("jwtToken").user(user).build();

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userService.createUser(any())).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("jwtToken");

        // Act
        JwtAuthenticationResponse actualResponse = authenticationService.signup(signUpRequest);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(userService, times(1)).createUser(any());
        verify(jwtService, times(1)).generateToken(any());
    }

    @Test
    void signup_FailedSignup_ReturnsEmptyJwtAuthenticationResponse() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest("passcode", 1L, "email@test.com", "cartId");

        when(userService.createUser(any())).thenReturn(null);

        // Act
        JwtAuthenticationResponse actualResponse = authenticationService.signup(signUpRequest);

        // Assert
        assertEquals(JwtAuthenticationResponse.builder().build(), actualResponse);
        verify(userService, times(1)).createUser(any());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void login_ValidRequest_ReturnsJwtAuthenticationResponse() {
        // Arrange
        SignInRequest signInRequest = new SignInRequest("passcode");
        User user = User.builder().username("username").email("email").password("encodedPassword").roleId(1L).cartId("cartId").build();
        JwtAuthenticationResponse expectedResponse = JwtAuthenticationResponse.builder().token("jwtToken").user(user).build();

        when(userDao.findByUsername(any())).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("jwtToken");

        // Act
        JwtAuthenticationResponse actualResponse = authenticationService.login(signInRequest);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(any());
    }

    @Test
    void login_InvalidPasscode_ThrowsIllegalArgumentException() {
        // Arrange
        SignInRequest signInRequest = new SignInRequest("passcode");

        when(userDao.findByUsername(any())).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> authenticationService.login(signInRequest));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(any());
    }
}
