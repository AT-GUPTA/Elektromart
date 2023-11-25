package com.elektrodevs.elektromart.controller;

import com.elektrodevs.elektromart.domain.User;
import com.elektrodevs.elektromart.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class StaffController {

    private final UserService userService;
    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllStaff() {
        log.info("Fetching all staff members");
        List<User> staffMembers = userService.getAllStaffMembers();
        return ResponseEntity.ok(staffMembers);
    }
    @GetMapping("/list-of-customers")
    public ResponseEntity<List<User>> getAllCustomers() {
        log.info("Fetching all customers");
        List<User> customers = userService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/grant")
    public ResponseEntity<?> grantStaffPrivileges(@RequestParam String username) {
        log.info("Received request to grant staff privileges to {}", username);
        userService.grantStaffPrivileges(username);
        log.info("Staff privileges granted to {}", username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/revoke")
    public ResponseEntity<?> revokeStaffPrivileges(@RequestParam String username) {
        log.info("Received request to revoke staff privileges from {}", username);
        userService.revokeStaffPrivileges(username);
        log.info("Staff privileges revoked from {}", username);
        return ResponseEntity.ok().build();
    }
}
