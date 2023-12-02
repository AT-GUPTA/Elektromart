package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.UserDao;
import com.elektrodevs.elektromart.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDao userDao;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                try {
                    UserDetails user = userDao.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                    log.debug("loadUserByUsername: Loaded user with username '{}'.", username);
                    return user;
                } catch (UsernameNotFoundException e) {
                    log.error("loadUserByUsername: User with username '{}' not found.", username);
                    throw e;
                }
            }
        };
    }
    public User createUser(User newUser) {
        User createdUser = userDao.createUser(newUser);
        if (createdUser != null) {
            log.debug("createUser: Created a new user with username '{}'.", newUser.getUsername());
        } else {
            log.error("createUser: Failed to create a new user.");
        }
        return createdUser;
    }
    public void grantStaffPrivileges(String username) {
        userDao.updateUserRole(username, User.ROLE_STAFF);
        log.info("Granted staff privileges to user {}", username);
    }

    public void revokeStaffPrivileges(String username) {
        userDao.updateUserRole(username, User.ROLE_CUSTOMER);
        log.info("Revoked staff privileges from user {}", username);
    }
    public List<User> getAllStaffMembers() {
        log.info("Fetching all staff members");
        try {
            List<User> staffMembers = userDao.getAllStaffMembers();
            log.info("Successfully fetched {} staff members", staffMembers.size());
            return staffMembers;
        } catch (Exception e) {
            log.error("Error occurred while fetching staff members: {}", e.getMessage());
            throw e;
        }
    }
    public List<User> getAllCustomers() {
        log.info("Fetching all customers");
        try {
            List<User> customers = userDao.getAllCustomers();
            log.info("Successfully fetched {} customers", customers.size());
            return customers;
        } catch (Exception e) {
            log.error("Error occurred while fetching customers: {}", e.getMessage());
            throw e;
        }
    }

    public boolean updateUserPasscode(String oldPasscode, String newPasscode) {
        log.info("Updating user passcode");
        return userDao.updateUserPasscode(oldPasscode, newPasscode);
    }
}
