package com.elektromart.service;

import com.elektromart.dao.UserDao;
import com.elektromart.domain.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User authenticateUser(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User createUser(User newUser) throws IllegalArgumentException {
        return userDao.createUser(newUser);
    }
}
