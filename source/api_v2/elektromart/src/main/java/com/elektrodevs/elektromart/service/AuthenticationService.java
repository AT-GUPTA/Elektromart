package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.UserDao;
import com.elektrodevs.elektromart.domain.User;
import com.elektrodevs.elektromart.dto.JwtAuthenticationResponse;
import com.elektrodevs.elektromart.dto.SignInRequest;
import com.elektrodevs.elektromart.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private static final String SECRET = "elektro@123";
    private final UserDao userDao;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(SignUpRequest request) {

        var user = User
                .builder()
                .username(request.getPasscode())
                .email(request.getEmail())
                .password(passwordEncoder.encode(SECRET))
                .roleId(request.getRole())
                .cartId(request.getCartId())
                .build();

        //fileService.writeUserToFile(user,request.getPassword()); removed as not a requirement from 3rd iteration
        user = userService.createUser(user);
        if (user != null) {
            log.debug("signup: User '{}' signed up successfully.", user.getUsername());
            var jwt = jwtService.generateToken(user);
            return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
        } else {
            log.error("signup: Failed to sign up user '{}' due to an error.", request.getPasscode());
            return JwtAuthenticationResponse.builder().build();
        }
    }


    public JwtAuthenticationResponse login(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPasscode(), SECRET));

        var user = userDao.findByUsername(request.getPasscode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid passcode"));
        var jwt = jwtService.generateToken(user);
        log.debug("login: User '{}' logged in successfully.", request.getPasscode());
        return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
    }

}