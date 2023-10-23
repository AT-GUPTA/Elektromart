package com.elektromart.controller;

import com.elektromart.domain.User;
import com.elektromart.service.UserService;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/*")
public class AuthenticationController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if ("/login".equals(path)) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            User user = userService.authenticateUser(email, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("id", user.getUserId());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("authorized", true);
                // Here you can also use a JSON response with relevant status.
                resp.setContentType("application/json");
                JsonObject jsonResponse = Json.createObjectBuilder()
                        .add("status", "SUCCESS")
                        .add("message", user.getUserId().toString())
                        .build();
                resp.getWriter().write(jsonResponse.toString());
            } else {
                resp.setContentType("application/json");
                JsonObject jsonResponse = Json.createObjectBuilder()
                        .add("status", "FAILURE")
                        .add("message", "Invalid email or password.")
                        .build();
                resp.getWriter().write(jsonResponse.toString());
            }

        } else if ("/logout".equals(path)) {
            HttpSession session = req.getSession();
            session.invalidate();
            resp.setContentType("application/json");
            JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("status", "SUCCESS")
                    .add("message", "Logged out successfully.")
                    .build();
            resp.getWriter().write(jsonResponse.toString());
        } else if ("/signup".equals(path)) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password);

            boolean isAccountCreated = userService.createUser(newUser);

            resp.setContentType("application/json");
            if (isAccountCreated) {
                JsonObject jsonResponse = Json.createObjectBuilder()
                        .add("status", "SUCCESS")
                        .add("message", "Account created!")
                        .build();
                resp.getWriter().write(jsonResponse.toString());
            } else {
                JsonObject jsonResponse = Json.createObjectBuilder()
                        .add("status", "FAILURE")
                        .add("message", "Account creation failed.")
                        .build();
                resp.getWriter().write(jsonResponse.toString());
            }
        }
    }
}
