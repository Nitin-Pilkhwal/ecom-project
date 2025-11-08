package com.demo.project.nitin.ecommerce.auth;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.entity.User;
import com.demo.project.nitin.ecommerce.repository.UserRepo;
import com.demo.project.nitin.ecommerce.service.helper.NotificationHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepository;
    private final NotificationHelperService notificationHelperService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email{}", email);
                    return new UsernameNotFoundException(MessageKeys.USER_NOT_FOUND);});
        return new CustomUserDetails(user);
    }

    public void setInvalidAttemptCount(User user, int count) {
        user.setInvalidAttemptCount(count);
        userRepository.save(user);
    }

    public void resetInvalidAttemptCount(User user) {
        user.setInvalidAttemptCount(0);
        userRepository.save(user);
    }

    public void lockUserAccount(User user) {
        user.setLocked(true);
        notificationHelperService.sendAccountLockedNotification(user);
        userRepository.save(user);
    }
}

