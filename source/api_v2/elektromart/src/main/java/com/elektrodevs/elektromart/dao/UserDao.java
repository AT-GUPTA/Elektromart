package com.elektrodevs.elektromart.dao;

import com.elektrodevs.elektromart.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
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
        return Optional.ofNullable(user);
    }


    public User getUser(String username) {
        String query = "SELECT * FROM Users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{username}, userRowMapper);
        } catch (EmptyResultDataAccessException e) {
            // User not found with the provided username
            return null;
        }
    }

    public User createUser(User newUser) {
        String cartId = UUID.randomUUID().toString();

        String cartQuery = "INSERT INTO Cart (id) VALUES (?)";
        jdbcTemplate.update(cartQuery, cartId);

        String userQuery = "INSERT INTO Users (username, email, password, role_id, status, cart_id) VALUES (?, ?, ?, ?, ?, ?)";
        String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());

        try {
            jdbcTemplate.update(userQuery,
                    newUser.getUsername(),
                    newUser.getEmail(),
                    hashedPassword,
                    newUser.getRoleId(),
                    newUser.getStatus(),
                    cartId);

            newUser.setCartId(cartId);
            return newUser;

        } catch (DuplicateKeyException e) {
            // Handle the case where the username or email already exists
            throw new IllegalArgumentException("Username or Email already exists.");
        }
    }

    public Integer getTotalUsers() {
        String userCount = "SELECT COUNT(*) FROM Users";
        return jdbcTemplate.queryForObject(userCount, Integer.class);
    }
}
