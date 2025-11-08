package com.demo.project.nitin.ecommerce.auth;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        if(!userDetails.isEnabled()){
            log.error("Account: {} is not active", username);
            throw new DisabledException(MessageKeys.USER_ACCOUNT_NOT_ACTIVE);
        }
        if(!userDetails.isAccountNonLocked()) {
            log.error("Account: {} is locked", username);
            throw new LockedException(MessageKeys.USER_ACCOUNT_LOCKED);
        }
        if(!userDetails.isAccountNonExpired()){
            log.error("Account: {} is expired", username);
            throw new AccountExpiredException(MessageKeys.USER_ACCOUNT_EXPIRED);
        }
        if(!userDetails.isCredentialsNonExpired()){
            log.error("Credentials for user: {} are expired", username);
            throw new CredentialsExpiredException(MessageKeys.USER_CREDENTIALS_EXPIRED);
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.error("Invalid password for user: {}", username);
            handleInvalidAttempt(userDetails, username);
            throw new BadCredentialsException(MessageKeys.INCORRECT_PASSWORD);
        }
        userDetailsService.resetInvalidAttemptCount(userDetails.getUser());
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

    private void handleInvalidAttempt(CustomUserDetails userDetails, String username) {
        int invalidAttemptCount = userDetails.getInvalidAttemptCount() + 1;
        log.info("Invalid attempt count for username {} is {}", username, invalidAttemptCount);
        userDetailsService.setInvalidAttemptCount(userDetails.getUser(), invalidAttemptCount);

        if (invalidAttemptCount >= Constants.Security.INVALID_ATTEMPTS) {
            userDetailsService.lockUserAccount(userDetails.getUser());
            log.info("User account is locked");
            throw new LockedException(MessageKeys.USER_ACCOUNT_LOCKED);
        }
    }

}
