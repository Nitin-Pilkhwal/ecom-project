package com.demo.project.nitin.ecommerce.constant.enums;

import lombok.Getter;

@Getter
public enum Authority {
    ADMIN("ROLE_ADMIN"),
    CUSTOMER("ROLE_CUSTOMER"),
    SELLER("ROLE_SELLER");

    private final String value;
    Authority(String value){
        this.value = value;
    }
}
