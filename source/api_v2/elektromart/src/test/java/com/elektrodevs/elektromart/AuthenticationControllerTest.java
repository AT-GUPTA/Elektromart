package com.elektrodevs.elektromart;

import com.elektrodevs.elektromart.controller.AuthenticationController;
import com.elektrodevs.elektromart.dto.JwtAuthenticationResponse;
import com.elektrodevs.elektromart.dto.SignUpRequest;
import com.elektrodevs.elektromart.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String passcode = UUID.randomUUID().toString().replace("-", "");
        signUpRequest.setPasscode(passcode);
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setRole(1L);
        signUpRequest.setCartId(UUID.randomUUID().toString());

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        jwtResponse.setToken("testToken");
        when(authenticationService.signup(any(SignUpRequest.class))).thenReturn(jwtResponse);

        // Act
        ResponseEntity<?> response = authenticationController.signup(signUpRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(authenticationService).signup(any(SignUpRequest.class));

//        JwtAuthenticationResponse loginRes = authenticationService.login(new SignInRequest(passcode));
//        assertNotNull(loginRes.getToken());
//        assertNotNull(loginRes.getUser());
//        assertEquals(loginRes.getUser().getUsername(), passcode);
    }


    /**
     * Tests the login functionality to ensure proper handling of valid login requests.
     */
//    @Test
//    public void testLogin() {
//        // Arrange
//        SignInRequest signInRequest = new SignInRequest();
//        signInRequest.setPasscode("adminTest"); // Use the passcode from signup test
//
//        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
//        jwtResponse.setToken("dummyToken");
//
//        when(authenticationService.login(any(SignInRequest.class))).thenReturn(jwtResponse);
//
//        // Act
//        ResponseEntity<?> response = authenticationController.login(signInRequest);
//
//        // Assert
//        assertEquals(200, response.getStatusCodeValue(), "Expected HTTP 200 OK response");
//        assertNotNull(response.getBody(), "Response body should not be null");
//
//        JwtAuthenticationResponse responseBody = (JwtAuthenticationResponse) response.getBody();
//        assertEquals("dummyToken", responseBody.getToken(), "Expected token to match dummy token");
//
//        verify(authenticationService).login(any(SignInRequest.class));
//    }

    /**
     * Tests change passcode functionality to ensure proper handling.
     */
}
