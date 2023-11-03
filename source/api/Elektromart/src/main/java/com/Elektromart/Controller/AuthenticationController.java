package com.elektromart.controller;

import com.elektromart.domain.User;
import com.elektromart.service.UserService;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

@WebServlet("/user/*")
public class AuthenticationController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        StringBuilder requestBody = extractRequestBody(req);

        if ("/login".equals(path)) {
            handleLogin(requestBody.toString(), req, resp);
        } else if ("/logout".equals(path)) {
            handleLogout(resp, req);
        } else if ("/signup".equals(path)) {
            handleSignup(requestBody.toString(), resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    private StringBuilder extractRequestBody(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody;
    }

    private void handleLogin(String requestBody, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JsonObject jsonInput = Json.createReader(new StringReader(requestBody)).readObject();
        String username = jsonInput.getString("username");
        String password = jsonInput.getString("password");

        User user = userService.authenticateUser(username, password);
        if (user != null) {
            createSessionForUser(req, user);
            sendJsonResponse(resp, "SUCCESS", user.getUserId().toString(), user.getCartId(), user.getRoleId().toString());
        } else {
            sendJsonResponse(resp, "FAILURE", "Invalid email or password.", null, null);
        }
    }

    private void createSessionForUser(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute("id", user.getUserId());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRoleId());
        session.setAttribute("authorized", true);
    }

    private void handleLogout(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        sendJsonResponse(resp, "SUCCESS", "Logged out successfully.", null, null);
    }

    private void handleSignup(String requestBody, HttpServletResponse resp) throws IOException {
        JsonObject jsonInput = Json.createReader(new StringReader(requestBody)).readObject();

        String username = jsonInput.getString("username");
        String email = jsonInput.getString("email");
        String password = jsonInput.getString("password");
        String role = jsonInput.getString("role");

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRoleId(Long.valueOf(role));
        newUser.setStatus("ACTIVE");

        try {
            newUser = userService.createUser(newUser);
            sendJsonResponse(resp, "SUCCESS", "Account created!", newUser.getCartId(), null);
        } catch (IllegalArgumentException e) {
            sendJsonResponse(resp, "FAILURE", e.getMessage(), null, null);
        }
    }

    private void sendJsonResponse(HttpServletResponse resp, String status, String message, String cartId, String roleId) throws IOException {
        resp.setContentType("application/json");
        JsonObjectBuilder jsonResponseBuilder = Json.createObjectBuilder()
                .add("status", status)
                .add("message", message);

        if (cartId != null) {
            jsonResponseBuilder.add("cartId", cartId);
        }
        if (roleId != null) {
            jsonResponseBuilder.add("roleId", roleId);
        }

        resp.getWriter().write(jsonResponseBuilder.build().toString());
    }
}
