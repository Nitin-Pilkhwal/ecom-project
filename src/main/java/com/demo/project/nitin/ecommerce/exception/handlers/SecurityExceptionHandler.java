package com.demo.project.nitin.ecommerce.exception.handlers;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.exception.ErrorDetails;
import com.demo.project.nitin.ecommerce.exception.exceptions.TokenExpiredException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class SecurityExceptionHandler {

    private final MessageSource messageSource;

    private String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticationException(
            BadCredentialsException ex,
            HttpServletRequest request
    ) {
        log.error("Bad Credential Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorDetails> handleLockedException(LockedException ex, HttpServletRequest request) {
        log.error("Lock Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.LOCKED);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorDetails> handleDisabledException(DisabledException ex, HttpServletRequest request) {
        log.error("Disabled Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity<ErrorDetails> handleAccountExpiredException(
            AccountExpiredException ex,
            HttpServletRequest request
    ) {
        log.error("Account Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ErrorDetails> handleCredentialsExpiredException(
            CredentialsExpiredException ex,
            HttpServletRequest request
    ) {
        log.error("Credential Expired Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorDetails> handleExpiredJwtException(ExpiredJwtException ex, HttpServletRequest request) {
        log.error("Jwt Expired Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(MessageKeys.TOKEN_ALREADY_EXPIRED),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorDetails> handleSignatureException(SignatureException ex, HttpServletRequest request){
        log.error("Jwt Signature Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(MessageKeys.TOKEN_SIGNATURE_INVALID),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorDetails> handleMalformedJwtException(
            MalformedJwtException ex,
            HttpServletRequest request
    ){
        log.error("Malformed Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(MessageKeys.TOKEN_MALFORMED),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorDetails> handleJwtException(JwtException ex, HttpServletRequest request) {
        log.error("Jwt Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(MessageKeys.INVALID_TOKEN),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDetails> handleNoResourceFoundException(
            NoResourceFoundException ex, HttpServletRequest request) {
        log.error("No Resource Found Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(MessageKeys.NOT_FOUND),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorDetails> handleTokenExpiredException(
            TokenExpiredException ex,
            HttpServletRequest request
    ) {
        log.error("Token Exception: {} on path {}",ex.getMessage(),request.getRequestURI());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}
