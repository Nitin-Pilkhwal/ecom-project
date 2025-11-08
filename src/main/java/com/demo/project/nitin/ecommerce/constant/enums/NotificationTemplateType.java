package com.demo.project.nitin.ecommerce.constant.enums;

import lombok.Getter;

@Getter
public enum NotificationTemplateType {
    ACCOUNT_VERIFICATION("Account Verification"),
    ACCOUNT_ACTIVATED("Account Activated"),
    ACCOUNT_DEACTIVATED("Account Deactivated"),
    ACCOUNT_LOCKED("Account Locked"),
    WAITING_FOR_APPROVAL("Waiting for Approval"),
    PASSWORD_RESET("Password Reset"),
    PASSWORD_UPDATED("Password Updated Successfully."),
    ACCOUNT_EXPIRED("Account Expired"),
    SELLER_PRODUCT_LISTED("Seller listed new Product"),
    OUT_OF_STOCK("Product out of stock"),
    PRODUCT_STATUS_UPDATE("Product status update");

    private final String subject;
    
    NotificationTemplateType(String subject) {
        this.subject = subject;
    }

}
