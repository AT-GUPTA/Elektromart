package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.UserDao;
import com.elektrodevs.elektromart.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDao userDao;

    public User findUserByPasscode(String passcode) {
        try {
            Optional<User> user = userDao.findByPasscode(passcode);
            if (user == null) {
                log.error("findUserByPasscode: User with passcode '{}' not found.", passcode);
                return null;
            }
            log.debug("findUserByPasscode: Loaded user with passcode '{}'.", passcode);
            return user.orElseThrow(() -> new BadCredentialsException("Invalid passcode"));
        } catch (Exception e) {
            log.error("findUserByPasscode: Error occurred while finding user with passcode '{}': {}", passcode, e.getMessage());
            throw e;
        }
    }

    public User createUser(User newUser) {
        User createdUser = userDao.createUser(newUser);
        if (createdUser != null) {
            log.debug("createUser: Created a new user with encrypted passcode '{}'.", newUser.getPasscode());
        } else {
            log.error("createUser: Failed to create a new user.");
        }
        return createdUser;
    }
    public void grantStaffPrivileges(String passcode) {
        userDao.updateUserRole(passcode, User.ROLE_STAFF);
        log.info("Granted staff privileges to user {}", passcode);
    }

    public void revokeStaffPrivileges(String passcode) {
        userDao.updateUserRole(passcode, User.ROLE_CUSTOMER);
        log.info("Revoked staff privileges from user {}", passcode);
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
}
