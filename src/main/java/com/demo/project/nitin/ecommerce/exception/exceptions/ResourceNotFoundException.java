package com.demo.project.nitin.ecommerce.exception.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String errorImageNotFound) {
        super(errorImageNotFound);
    }
}
