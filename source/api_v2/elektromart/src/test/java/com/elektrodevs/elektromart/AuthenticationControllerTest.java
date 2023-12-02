package com.elektrodevs.elektromart;

import com.elektrodevs.elektromart.controller.AuthenticationController;
import com.elektrodevs.elektromart.dto.JwtAuthenticationResponse;
import com.elektrodevs.elektromart.dto.SignInRequest;
import com.elektrodevs.elektromart.dto.SignUpRequest;
import com.elektrodevs.elektromart.service.AuthenticationService;
import com.elektrodevs.elektromart.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for login and sigunp with passcode
 */
@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationController authenticationController;


    /**
     * Tests the login functionality to ensure proper handling of valid login requests.
     */
    @Test
    public void testLogin() {
        // Arrange
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("testUser");
        signInRequest.setPassword("testPass");

        UserDetails mockUserDetails = new User("testUser", "password123", Collections.emptyList());
        String dummyToken = "dummyToken";
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        jwtResponse.setToken(dummyToken);

        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(dummyToken);
        when(authenticationService.login(any(SignInRequest.class))).thenReturn(jwtResponse);

        // Act
        ResponseEntity<?> response = authenticationController.login(signInRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue(), "Expected HTTP 200 OK response");
        assertNotNull(response.getBody(), "Response body should not be null");

        JwtAuthenticationResponse responseBody = (JwtAuthenticationResponse) response.getBody();
        assertEquals(dummyToken, responseBody.getToken(), "Expected token to match dummy token");

        verify(authenticationService).login(any(SignInRequest.class));
    }


    /**
     * Tests the signup functionality to ensure that new users can register correctly.
     */
    @Test
    public void testSignup() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password123");
        signUpRequest.setUsername("testUser");

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        jwtResponse.setToken("testToken");
        when(authenticationService.signup(any(SignUpRequest.class))).thenReturn(jwtResponse);

        // Act
        ResponseEntity<?> response = authenticationController.signup(signUpRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(authenticationService).signup(any(SignUpRequest.class));
    }
}
