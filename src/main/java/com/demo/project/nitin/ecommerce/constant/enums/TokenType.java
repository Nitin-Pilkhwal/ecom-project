package com.demo.project.nitin.ecommerce.constant.enums;

import lombok.Getter;

@Getter
public enum TokenType {
    REFRESH("refresh_token"),
    ACTIVATION("activation_token"),
    FORGOT_PASSWORD("forgot_password_token");

    private final String type;
    TokenType(String type) {
        this.type = type;
    }
}
