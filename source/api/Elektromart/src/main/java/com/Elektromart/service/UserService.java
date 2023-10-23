package com.elektromart.service;

import com.elektromart.dao.UserDao;
import com.elektromart.domain.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User authenticateUser(String email, String password) {
        User user = userDao.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean createUser(User newUser) {
        return userDao.createUser(newUser);
    }
}
