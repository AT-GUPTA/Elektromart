package com.elektromart.utils;

import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpSession;

public class AuthUtils {
    
    public static boolean isAuthorized(HttpSession session) {
        return session != null && Boolean.TRUE.equals(session.getAttribute("authorized"));
    }

    public static String getUsername(HttpSession session) {
        return session == null ? null : (String) session.getAttribute("username");
    }

    // Hash a password using BCrypt
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Check if a plain text password matches its hash
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

}
