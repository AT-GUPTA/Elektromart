package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.UserDao;
import com.elektrodevs.elektromart.domain.User;
import com.elektrodevs.elektromart.dto.JwtAuthenticationResponse;
import com.elektrodevs.elektromart.dto.SignInRequest;
import com.elektrodevs.elektromart.dto.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

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

    public JwtAuthenticationResponse signup(SignUpRequest request) {

        var user = User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleId(request.getRole())
                .cartId(request.getCartId())
                .build();

        fileService.writeUserToFile(user,request.getPassword());
        user = userService.createUser(user);
        if (user != null) {
            log.debug("signup: User '{}' signed up successfully.", user.getUsername());
            var jwt = jwtService.generateToken(user);
            return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
        } else {
            log.error("signup: Failed to sign up user '{}' due to an error.", request.getUsername());
            return JwtAuthenticationResponse.builder().build();
        }
    }


    public JwtAuthenticationResponse login(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = userDao.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));
        var jwt = jwtService.generateToken(user);
        log.debug("login: User '{}' logged in successfully.", request.getUsername());
        return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
    }

}