package com.elektromart.utils;

import javax.servlet.http.HttpSession;

public class AuthUtils {

    public static boolean isAuthorized(HttpSession session) {
        return session != null && Boolean.TRUE.equals(session.getAttribute("authorized"));
    }

    public static String getUsername(HttpSession session) {
        return session == null ? null : (String) session.getAttribute("username");
    }
}
