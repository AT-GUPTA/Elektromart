package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.User;
import com.elektrodevs.elektromart.dto.JwtAuthenticationResponse;
import com.elektrodevs.elektromart.dto.SignInRequest;
import com.elektrodevs.elektromart.dto.SignUpRequest;
import com.elektrodevs.elektromart.service.AuthenticationService;
import com.elektrodevs.elektromart.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

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
    public void signup_shouldReturnSuccessReponse_WhenSignupIsCalledWithAdequateParameters() {
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
    }

    @Mock
    private UserService userService;

    @Test
    void login_ShouldReturnSuccessResponse_WhenLoginIsSuccessful() {
        // Arrange
        SignInRequest signInRequest = new SignInRequest("password");
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        User user = new User();
        user.setUserId(1L);
        user.setUsername("password");
        jwtAuthenticationResponse.setUser(user);

        jwtAuthenticationResponse.setToken("testToken");
        when(authenticationService.login(signInRequest)).thenReturn(jwtAuthenticationResponse);

        // Act
        ResponseEntity<?> responseEntity = authenticationController.login(signInRequest);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("SUCCESS", ((Map<?, ?>) responseEntity.getBody()).get("status"));
        assertTrue(((Map<?, ?>) responseEntity.getBody()).containsKey("token"));
        verify(authenticationService).login(signInRequest);
    }

    @Test
    void login_ShouldReturnFailureResponse_WhenLoginFails() {
        // Arrange
        SignInRequest signInRequest = new SignInRequest("password");

        when(authenticationService.login(signInRequest)).thenReturn(null);

        // Act
        ResponseEntity<?> responseEntity = authenticationController.login(signInRequest);

        // Assert
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("FAILURE", ((Map<?, ?>) responseEntity.getBody()).get("status"));
        assertTrue(((Map<?, ?>) responseEntity.getBody()).containsKey("message"));
        verify(authenticationService).login(signInRequest);
    }

    @Test
    void changePasscode_ShouldReturnUnprocessableEntity_WhenNewPasscodeAndConfirmationDoNotMatch() {
        // Arrange
        AuthenticationController.PasscodeChangeRequest passcodeChangeRequest = new AuthenticationController.PasscodeChangeRequest();
        passcodeChangeRequest.setOldPasscode("oldPasscode");
        passcodeChangeRequest.setNewPasscode("newPasscode");
        passcodeChangeRequest.setNewPasscodeConfirmation("differentNewPasscodeConfirmation");

        // Act
        ResponseEntity<?> responseEntity = authenticationController.ChangePasscode(passcodeChangeRequest);

        // Assert
        assertEquals(422, responseEntity.getStatusCodeValue());
        verify(userService, never()).updateUserPasscode(anyString(), anyString());
    }

    @Test
    void changePasscode_ShouldUpdatePasscode_WhenNewPasscodeAndConfirmationMatch() {
        // Arrange
        AuthenticationController.PasscodeChangeRequest passcodeChangeRequest = new AuthenticationController.PasscodeChangeRequest();
        passcodeChangeRequest.setOldPasscode("oldPasscode");
        passcodeChangeRequest.setNewPasscode("newPasscode");
        passcodeChangeRequest.setNewPasscodeConfirmation("newPasscode");

        // Act
        ResponseEntity<?> responseEntity = authenticationController.ChangePasscode(passcodeChangeRequest);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(userService).updateUserPasscode("oldPasscode", "newPasscode");
    }
}
