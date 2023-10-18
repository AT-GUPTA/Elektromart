package com.elektromart.controller;

import com.elektromart.domain.User;
import com.elektromart.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth/*")
public class AuthenticationController extends HttpServlet {

    private final UserDao userDao = new UserDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if ("/login".equals(path)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (userDao.validateUser(username, password)) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                session.setAttribute("authorized", true);
                resp.sendRedirect("/products");
            } else {
                resp.sendRedirect("/login?error=invalid");
            }

        } else if ("/signup".equals(path)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

//            User newUser = new User(username, password, false); //todo: add email and role
//            boolean success = userDao.addUser(newUser);//todo: add email and role
            boolean success = false;
            if (success) {
                resp.sendRedirect("/login");//todo change response object
            } else {
                resp.sendRedirect("/signup?error=exists");//todo change response object
            }
        }
    }
}
