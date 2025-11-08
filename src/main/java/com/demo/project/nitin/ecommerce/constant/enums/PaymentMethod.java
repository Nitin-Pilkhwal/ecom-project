package com.demo.project.nitin.ecommerce.constant.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    UPI("upi"),
    NET_BANKING("Net Banking"),
    CASH_ON_DELIVERY("Cash on delivery");

    private final String paymentMode;

    private PaymentMethod(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}
