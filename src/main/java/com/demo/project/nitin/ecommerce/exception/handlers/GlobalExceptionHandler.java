package com.demo.project.nitin.ecommerce.exception.handlers;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.exception.ErrorDetails;
import com.demo.project.nitin.ecommerce.exception.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<ErrorDetails> handleDuplicateField(DuplicateFieldException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<ErrorDetails> handleEmailServiceException(
            EmailServiceException ex,
            HttpServletRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUsernameNotFoundException(
            HttpServletRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(MessageKeys.USER_NOT_FOUND),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handleSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException ex,
            HttpServletRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDetails> handleMaxSizeException(HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(MessageKeys.FILE_SIZE_EXCEEDED),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<ErrorDetails> handleFileException(FileException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}