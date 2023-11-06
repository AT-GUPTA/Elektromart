package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.User;
import com.elektrodevs.elektromart.dto.JwtAuthenticationResponse;
import com.elektrodevs.elektromart.dto.SignInRequest;
import com.elektrodevs.elektromart.dto.SignUpRequest;
import com.elektrodevs.elektromart.service.AuthenticationService;
import com.elektrodevs.elektromart.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody SignInRequest request) {
        try {
            JwtAuthenticationResponse result = authenticationService.login(request);
            if (result != null && result.getUser() != null){
                return ResponseEntity.ok(createJsonResponse("SUCCESS", result.getUser().getUserId().toString(),result));
            } else {
                return ResponseEntity.badRequest().body(createJsonResponse("FAILURE", "Invalid email or password.", null));
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(createJsonResponse("FAILURE", e.getMessage(), null));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> handleSignup(@RequestBody SignUpRequest request) {
        try {
            JwtAuthenticationResponse result = authenticationService.signup(request);
            return ResponseEntity.ok(createJsonResponse("SUCCESS", "Account created!",result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createJsonResponse("FAILURE", e.getMessage(), null));
        }
    }
    private Map<String, Object> createJsonResponse(String status, String message, JwtAuthenticationResponse resp) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("token",resp.getToken());

        if (resp.getUser().getCartId() != null) {
            response.put("cartId", resp.getUser().getCartId());
        }
        if (resp.getUser().getRoleId() != null) {
            response.put("roleId", resp.getUser().getRoleId());
        }

        return response;
    }
}
