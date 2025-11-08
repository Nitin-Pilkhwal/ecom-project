package com.demo.project.nitin.ecommerce.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private Map<String, String> fieldErrors;

    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public ErrorDetails(LocalDateTime timestamp, String message, String details, Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    @Override
    public String toString() {
        return """
           {
             "timestamp": "%s",
             "message": "%s",
             "details": "%s",
             "fieldErrors": %s
           }
           """.formatted(timestamp, message, details, fieldErrors != null ? fieldErrors.toString() : "null");
    }

}