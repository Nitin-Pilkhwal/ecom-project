package com.demo.project.nitin.ecommerce.constant.enums;

import lombok.Getter;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Getter
public enum OrderStatus {

    ORDER_PLACED("Order is placed"),
    ORDER_CONFIRMED("Order is confirmed"),
    CANCELLED("Order is cancelled"),
    ORDER_REJECTED("Order is rejected"),
    ORDER_SHIPPED("Order is shipped"),
    DELIVERED("Order is successfully delivered"),
    CLOSED("Order is closed"),

    REFUND_INITIATED("Refund is initiated"),
    REFUND_COMPLETED("Refund is completed"),

    RETURN_REQUESTED("Order return is requested"),
    RETURN_REJECTED("Order return is rejected"),
    RETURN_APPROVED("Order return is approved"),

    PICK_UP_INITIATED("Order pickup is initiated"),
    PICK_UP_COMPLETED("Order pickup is completed");

    private final String message;

    OrderStatus(String message) {
        this.message = message;
    }

    private static final Map<OrderStatus, Set<OrderStatus>> allowedTransitions = new EnumMap<>(OrderStatus.class);

    static {
        allowedTransitions.put(OrderStatus.ORDER_PLACED, Set.of(
                OrderStatus.CANCELLED,
                OrderStatus.ORDER_CONFIRMED,
                OrderStatus.ORDER_REJECTED
        ));
        allowedTransitions.put(OrderStatus.CANCELLED, Set.of(
                OrderStatus.REFUND_INITIATED,
                OrderStatus.CLOSED
        ));
        allowedTransitions.put(OrderStatus.ORDER_REJECTED, Set.of(
                OrderStatus.REFUND_INITIATED,
                OrderStatus.CLOSED
        ));
        allowedTransitions.put(OrderStatus.ORDER_CONFIRMED, Set.of(
                OrderStatus.CANCELLED,
                OrderStatus.ORDER_SHIPPED
        ));
        allowedTransitions.put(OrderStatus.ORDER_SHIPPED, Set.of(
                OrderStatus.DELIVERED
        ));
        allowedTransitions.put(OrderStatus.DELIVERED, Set.of(
                OrderStatus.RETURN_REQUESTED,
                OrderStatus.CLOSED
        ));
        allowedTransitions.put(OrderStatus.RETURN_REQUESTED, Set.of(
                OrderStatus.RETURN_REJECTED,
                OrderStatus.RETURN_APPROVED
        ));
        allowedTransitions.put(OrderStatus.RETURN_REJECTED, Set.of(
                OrderStatus.CLOSED
        ));
        allowedTransitions.put(OrderStatus.RETURN_APPROVED, Set.of(
                OrderStatus.PICK_UP_INITIATED
        ));
        allowedTransitions.put(OrderStatus.PICK_UP_INITIATED, Set.of(
                OrderStatus.PICK_UP_COMPLETED
        ));
        allowedTransitions.put(OrderStatus.PICK_UP_COMPLETED, Set.of(
                OrderStatus.REFUND_INITIATED
        ));
        allowedTransitions.put(OrderStatus.REFUND_INITIATED, Set.of(
                OrderStatus.REFUND_COMPLETED
        ));
        allowedTransitions.put(OrderStatus.REFUND_COMPLETED, Set.of(
                OrderStatus.CLOSED
        ));
    }

    public static boolean isValidTransition(OrderStatus from, OrderStatus to) {
        return allowedTransitions.containsKey(from) && allowedTransitions.get(from).contains(to);
    }
}
