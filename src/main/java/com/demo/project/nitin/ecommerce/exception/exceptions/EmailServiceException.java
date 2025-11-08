package com.demo.project.nitin.ecommerce.exception.exceptions;

public class EmailServiceException extends RuntimeException {
    public EmailServiceException(String failedToSendTheMail, Exception e) {
        super(failedToSendTheMail, e);
    }
}
