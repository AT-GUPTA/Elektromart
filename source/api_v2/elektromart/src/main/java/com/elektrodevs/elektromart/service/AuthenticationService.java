package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.UserDao;
import com.elektrodevs.elektromart.domain.User;
import com.elektrodevs.elektromart.dto.JwtAuthenticationResponse;
import com.elektrodevs.elektromart.dto.SignInRequest;
import com.elektrodevs.elektromart.dto.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserDao userDao;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FileService fileService;

    private static final String SECRET = "elektro@123";

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User
                .builder()
                .passcode(passwordEncoder.encode(request.getPasscode() + SECRET))
                .email(request.getEmail())
                .roleId(request.getRole())
                .cartId(request.getCartId())
                .build();

        fileService.writeUserToFile(user, request.getPasscode());
        user = userService.createUser(user);
        if (user != null) {
            log.debug("signup: User '{}' signed up successfully.", user);
            var jwt = jwtService.generateToken(user.getPasscode());
            return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
        } else {
            log.error("signup: Failed to sign up user due to an error.");
            return JwtAuthenticationResponse.builder().build();
        }
    }


    public JwtAuthenticationResponse login(SignInRequest request) {
        String rawPasscode = request.getPasscode() + SECRET;
        List<User> users = userDao.getAllUsers();

        User authenticatedUser = null;
        for (User user : users) {
            if (passwordEncoder.matches(rawPasscode, user.getPasscode())) {
                authenticatedUser = user;
                break;
            }
        }

        if (authenticatedUser == null) {
            throw new IllegalArgumentException("Invalid passcode");
        }

        // Continue with the login process
        var jwt = jwtService.generateToken(authenticatedUser.getPasscode());
        log.debug("login: User with passcode '{}' logged in successfully.", request.getPasscode());
        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .user(authenticatedUser)
                .build();
    }

}