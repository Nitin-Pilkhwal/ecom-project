package com.demo.project.nitin.ecommerce.constant.enums;

import lombok.Getter;

@Getter
public enum JwtTokenType {
    ACCESS("access_token"),
    REFRESH("refresh_token");

    private final String type;

    JwtTokenType(String type) {
        this.type = type;
    }
}

