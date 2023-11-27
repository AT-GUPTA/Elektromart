package com.elektrodevs.elektromart.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {

    private Long userId;
    private String passcode; // New field for passcode
    private String email;
    private Long roleId;
    private String status;
    private String cartId;

    public static final long ROLE_CUSTOMER = 1;
    public static final long ROLE_STAFF = 2;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roleId == ROLE_CUSTOMER ? "ROLE_CUSTOMER" : "ROLE_STAFF"));
    }

    @Override
    public String getUsername() {
       return email;
    }

    @Override
    public String getPassword() {
        // Return the passcode
        return passcode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
