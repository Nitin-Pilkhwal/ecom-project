package com.demo.project.nitin.ecommerce.exception.handlers;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.exception.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler {
    private final MessageSource messageSource;
    Locale locale = LocaleContextHolder.getLocale();

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, locale);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDetails> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex,
            HttpServletRequest request) {
        String errorMessage = ex.getMessage();
        String message = messageSource.getMessage(MessageKeys.MISSING_REQUIRED_REQUEST_PARAM, null, locale) +
                errorMessage.substring(errorMessage.indexOf("parameter ") + 10, errorMessage.lastIndexOf("' ") + 1);
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleHttpMessageNotReadableException(
            HttpServletRequest request
    ) {
        String message = messageSource.getMessage(MessageKeys.JSON_MALFORMED,null,locale);
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDetails> handleHttpRequestMethodNotSupportedException(
            HttpServletRequest request
    ){
        String message = messageSource.getMessage(MessageKeys.HTTP_METHOD_NOT_SUPPORTED,null,locale);
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(MessageKeys.VALIDATION_FAILED),
                request.getRequestURI(),
                errors
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDetails> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                getMessage(ex.getMessage()),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
