package com.demo.project.nitin.ecommerce.exception.exceptions;

public class FileException extends RuntimeException{
    public FileException(String errorImageSave) {
        super(errorImageSave);
    }
}
