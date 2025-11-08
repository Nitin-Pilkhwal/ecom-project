package com.demo.project.nitin.ecommerce.auth;


import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    public int getInvalidAttemptCount() {
        return user.getInvalidAttemptCount();
    }

    public User getUser() { return user; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority().getValue()))
                .toList();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.isExpired();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getPasswordUpdateDate() == null ||
               user.getPasswordUpdateDate().isAfter(
                       LocalDate.now().minusDays(Constants.Security.PASSWORD_EXPIRY_DAYS)
               );
    }
}

