package com.elektrodevs.elektromart.dao;

import com.elektrodevs.elektromart.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRoleId(rs.getLong("role_id"));
            user.setStatus(rs.getString("status"));
            user.setCartId(rs.getString("cart_id"));
            return user;
        }
    };
    public Optional<User> findByUsername(String username){
        User user = getUser(username);
        log.debug("findByUsername: Username {} found in the database.", username);
        return Optional.ofNullable(user);
    }


    public User getUser(String username) {
        String query = "SELECT * FROM Users WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(query, new Object[]{username}, userRowMapper);
            log.debug("getUser: User with username {} found in the database.", username);
            return user;
        } catch (EmptyResultDataAccessException e) {
            log.debug("getUser: User with username {} not found in the database.", username);
            return null;
        }
    }

    public User createUser(User newUser) {
        String cartId = (newUser.getCartId()==null||newUser.getCartId().equalsIgnoreCase("undefined")||Objects.equals(newUser.getCartId(), "0"))?UUID.randomUUID().toString():newUser.getCartId();

        if(newUser.getCartId()==null||newUser.getCartId().equalsIgnoreCase("undefined")||Objects.equals(newUser.getCartId(), "0")) {
            String cartQuery = "INSERT INTO Cart (id) VALUES (?)";
            jdbcTemplate.update(cartQuery, cartId);
        }

        String userQuery = "INSERT INTO Users (username, email, password, role_id, status, cart_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(userQuery,
                    newUser.getUsername(),
                    newUser.getEmail(),
                    newUser.getPassword(),
                    newUser.getRoleId(),
                    newUser.getStatus(),
                    cartId);

            newUser.setCartId(cartId);
            log.debug("createUser: User {} created successfully.", newUser.getUsername());
            return newUser;

        } catch (DuplicateKeyException e) {
            log.error("createUser: Username or Email already exists for user {}.", newUser.getUsername());
            throw new IllegalArgumentException("Username or Email already exists.");
        }
    }

    public Integer getTotalUsers() {
        String userCount = "SELECT COUNT(*) FROM Users";
        Integer totalUsers = jdbcTemplate.queryForObject(userCount, Integer.class);
        log.debug("getTotalUsers: Total users in the database: {}", totalUsers);
        return totalUsers;
    }

    public void updateUserRole(String username, Long newRoleId) {
        String query = "UPDATE Users SET role_id = ? WHERE username = ?";
        int updatedRows = jdbcTemplate.update(query, newRoleId, username);

        if (updatedRows > 0) {
            log.info("UserRole updated for user {}. New RoleId: {}", username, newRoleId);
        } else {
            log.warn("No rows updated for user {}. RoleId not changed.", username);
        }
    }
    public List<User> getAllStaffMembers() {
        String query = "SELECT * FROM Users WHERE role_id = ?";
        try {
            List<User> staffMembers = jdbcTemplate.query(query, new Object[]{User.ROLE_STAFF}, userRowMapper);
            log.info("Retrieved {} staff members from the database", staffMembers.size());
            return staffMembers;
        } catch (Exception e) {
            log.error("Error retrieving staff members: {}", e.getMessage());
            throw e;
        }
    }
    public List<User> getAllCustomers() {
        String query = "SELECT * FROM Users WHERE role_id = ?";
        try {
            List<User> customers = jdbcTemplate.query(query, new Object[]{User.ROLE_CUSTOMER}, userRowMapper);
            log.info("Retrieved {} customers from the database", customers.size());
            return customers;
        } catch (Exception e) {
            log.error("Error retrieving customer: {}", e.getMessage());
            throw e;
        }
    }


}
