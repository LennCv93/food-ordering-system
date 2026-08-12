package com.foodorder.order.domain.model;

public enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAID,
    PAYMENT_FAILED,
    PREPARING,
    IN_DELIVERY,
    DELIVERED,
    CANCELLED
}
