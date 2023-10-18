package com.elektromart.dao;

import com.elektromart.domain.User;

public class UserDao {

    public User getUserByUsername(String username) {
        // Retrieve user details from H2 database using JDBC and return as User object
        return null;
    }

    public Boolean addUser(User user) {
        // Insert a new user into the H2 database and return success/failure
        return null;
    }

    public Boolean validateUser(String username, String password) {
        // Validate user credentials against database
        return null;
    }
}
