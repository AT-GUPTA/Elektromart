package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.User;
import com.elektrodevs.elektromart.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody Map<String, String> credentials, HttpSession session) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = userService.authenticateUser(username, password);
        if (user != null) {
            createSessionForUser(session, user);
            return ResponseEntity.ok(createJsonResponse("SUCCESS", user.getUserId().toString(), user.getCartId(), user.getRoleId().toString()));
        } else {
            return ResponseEntity.badRequest().body(createJsonResponse("FAILURE", "Invalid email or password.", null, null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> handleLogout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(createJsonResponse("SUCCESS", "Logged out successfully.", null, null));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
    public ResponseEntity<?> handleSignup(@RequestBody Map<String, String> userDetails) {
        User newUser = new User();
        newUser.setUsername(userDetails.get("username"));
        newUser.setEmail(userDetails.get("email"));
        newUser.setPassword(userDetails.get("password"));
        newUser.setRoleId(Long.valueOf(userDetails.get("role")));
        newUser.setStatus("ACTIVE");

        try {
            newUser = userService.createUser(newUser);
            return ResponseEntity.ok(createJsonResponse("SUCCESS", "Account created!", newUser.getCartId(), null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(createJsonResponse("FAILURE", e.getMessage(), null, null));
        }
    }

    private void createSessionForUser(HttpSession session, User user) {
        session.setAttribute("id", user.getUserId());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRoleId());
        session.setAttribute("authorized", true);
    }

    private Map<String, Object> createJsonResponse(String status, String message, String cartId, String roleId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);

        if (cartId != null) {
            response.put("cartId", cartId);
        }
        if (roleId != null) {
            response.put("roleId", roleId);
        }

        return response;
    }
}
