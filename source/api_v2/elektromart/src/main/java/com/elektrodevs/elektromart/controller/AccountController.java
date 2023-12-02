package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class AccountController {
    private final UserService userService;

    @PostMapping("/passcode")
    public ResponseEntity<?> ChangePasscode(@RequestBody PasscodeChangeRequest request) {
        log.info("Received request to change passcodeprivileges to {}", request.getOldPasscode());

        if (!request.getNewPasscode().equals(request.getNewPasscodeConfirmation())) {
            log.error("New passcode and new passcode confirmation do not match.");
            return ResponseEntity.unprocessableEntity().build();
        }

        userService.updateUserPasscode(request.getOldPasscode(), request.getNewPasscode());
        log.info("Update user passcode from {} to {}", request.getOldPasscode(), request.getNewPasscode());
        return ResponseEntity.ok().build();
    }

    @Data
    static
    private class PasscodeChangeRequest {
        private String oldPasscode;
        private String newPasscode;
        private String newPasscodeConfirmation;
    }
}
