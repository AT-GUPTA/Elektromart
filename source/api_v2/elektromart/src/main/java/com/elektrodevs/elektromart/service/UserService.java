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
    }}
