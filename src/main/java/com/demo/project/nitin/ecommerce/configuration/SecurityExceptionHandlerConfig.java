package com.demo.project.nitin.ecommerce.configuration;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.exception.ErrorDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.time.LocalDateTime;
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class SecurityExceptionHandlerConfig {

    private final MessageSource messageSource;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            Locale locale = request.getLocale();
            String message = messageSource.getMessage(MessageKeys.AUTH_REQUIRED, null, locale);
            ErrorDetails errorDetails = new ErrorDetails(
                    LocalDateTime.now(),
                    message,
                    request.getRequestURI()
            );
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(errorDetails.toString());
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            Locale locale = request.getLocale();
            String message = messageSource.getMessage(MessageKeys.ACCESS_DENIED, null, locale);
            ErrorDetails errorDetails = new ErrorDetails(
                    LocalDateTime.now(),
                    message,
                    request.getRequestURI()
            );
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write(errorDetails.toString());
        };
    }
}

