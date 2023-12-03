package com.elektrodevs.elektromart;

import com.elektrodevs.elektromart.controller.AuthenticationController;
import com.elektrodevs.elektromart.dto.JwtAuthenticationResponse;
import com.elektrodevs.elektromart.dto.SignInRequest;
import com.elektrodevs.elektromart.dto.SignUpRequest;
import com.elektrodevs.elektromart.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for login and sigunp and change passcode
 */
@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;


    /**
     * Tests the signup functionality to ensure that new users can register correctly.
     */
    @Test
    public void testSignup() {
        // Arrange
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
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


    /**
     * Tests the login functionality to ensure proper handling of valid login requests.
     */
    @Test
    public void testLogin() {
        // Arrange
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("testUser");
        // No password is set here

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        jwtResponse.setToken("dummyToken");

        when(authenticationService.login(any(SignInRequest.class))).thenReturn(jwtResponse);

        // Act
        ResponseEntity<?> response = authenticationController.login(signInRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue(), "Expected HTTP 200 OK response");
        assertNotNull(response.getBody(), "Response body should not be null");

        JwtAuthenticationResponse responseBody = (JwtAuthenticationResponse) response.getBody();
        assertEquals("dummyToken", responseBody.getToken(), "Expected token to match dummy token");

        verify(authenticationService).login(any(SignInRequest.class));
    }

    /**
     * Tests change passcode functionality to ensure proper handling.
     */
}
