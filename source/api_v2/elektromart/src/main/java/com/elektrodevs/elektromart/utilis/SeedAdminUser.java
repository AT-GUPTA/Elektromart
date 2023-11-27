package com.elektrodevs.elektromart.utilis;

import com.elektrodevs.elektromart.dao.UserDao;
import com.elektrodevs.elektromart.domain.User;
import com.elektrodevs.elektromart.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedAdminUser implements CommandLineRunner {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        if (userDao.getTotalUsers() == 0) {

            User admin = User
                    .builder()
                    .email("admin@test.com")
                    .passcode(passwordEncoder.encode("admin"))
                    .roleId(2L)
                    .build();

            userService.createUser(admin);
            log.debug("created ADMIN user - {}", admin);
        }
    }

}