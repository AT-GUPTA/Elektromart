package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.dto.JwtAuthenticationResponse;
import com.elektrodevs.elektromart.dto.SignInRequest;
import com.elektrodevs.elektromart.dto.SignUpRequest;
import com.elektrodevs.elektromart.service.AuthenticationService;
import com.elektrodevs.elektromart.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for authentication requests handling user login and signup.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    /**
     * Endpoint for user login.
     *
     * @param request The sign-in request containing credentials.
     * @return A response entity containing the JWT or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInRequest request) {
        log.debug("Attempting to login user: {}", request.getPasscode());
        try {
            JwtAuthenticationResponse result = authenticationService.login(request);
            if (result != null && result.getUser() != null) {
                log.debug("Login successful for user: {}", request.getPasscode());
                return ResponseEntity.ok(createJsonResponse("SUCCESS", result.getUser().getUserId().toString(), result));
            } else {
                log.error("Login failed for user: {}", request.getPasscode());
                return ResponseEntity.badRequest().body(createJsonResponse("FAILURE", "Invalid email or password.", new JwtAuthenticationResponse()));
            }
        } catch (Exception e) {
            log.error("Login exception for user: {}: {}", request.getPasscode(), e.getMessage());
            return ResponseEntity.badRequest().body(createJsonResponse("FAILURE", e.getMessage(), new JwtAuthenticationResponse()));
        }
    }

    /**
     * Endpoint for user registration.
     *
     * @param request The sign-up request containing user information.
     * @return A response entity with the result of the registration attempt.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        log.debug("Attempting to signup user: {}", request.getEmail());
        try {
            JwtAuthenticationResponse result = authenticationService.signup(request);
            log.debug("Signup successful for user: {}", request.getEmail());
            return ResponseEntity.ok(createJsonResponse("SUCCESS", "Account created!", result));
        } catch (Exception e) {
            log.error("Signup exception for user: {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body(createJsonResponse("FAILURE", e.getMessage(), new JwtAuthenticationResponse()));
        }
    }

    /**
     * Updates a user's passcode
     *
     * @param request
     * @return
     */
    @PostMapping("/passcode")
    public ResponseEntity<?> ChangePasscode(@RequestBody AuthenticationController.PasscodeChangeRequest request) {
        log.info("Received request to change passcode to {}", request.getOldPasscode());

        if (!request.getNewPasscode().equals(request.getNewPasscodeConfirmation())) {
            log.error("New passcode and new passcode confirmation do not match.");
            return ResponseEntity.unprocessableEntity().build();
        }

        userService.updateUserPasscode(request.getOldPasscode(), request.getNewPasscode());
        log.info("Update user passcode from {} to {}", request.getOldPasscode(), request.getNewPasscode());
        return ResponseEntity.ok().build();
    }

    /**
     * Helper method to create a JSON response map.
     *
     * @param status  The status of the response, typically "SUCCESS" or "FAILURE".
     * @param message The message to be included in the response.
     * @param resp    The JwtAuthenticationResponse object containing token and user information.
     * @return A map representing the JSON response.
     */
    private Map<String, Object> createJsonResponse(String status, String message, JwtAuthenticationResponse resp) {
        log.debug("Creating JSON response with status: {} and message: {}", status, message);
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("token", resp.getToken() != null ? resp.getToken() : "");

        if (resp.getUser() != null && resp.getUser().getCartId() != null) {
            response.put("cartId", resp.getUser().getCartId());
        }
        if (resp.getUser() != null && resp.getUser().getRoleId() != null) {
            response.put("roleId", resp.getUser().getRoleId());
        }

        return response;
    }

    @Data
    static
    public class PasscodeChangeRequest {
        private String oldPasscode;
        private String newPasscode;
        private String newPasscodeConfirmation;
    }
}
