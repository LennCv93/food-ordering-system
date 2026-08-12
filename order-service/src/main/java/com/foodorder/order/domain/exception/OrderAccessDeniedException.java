package com.foodorder.order.domain.exception;

public class OrderAccessDeniedException extends RuntimeException {

    public OrderAccessDeniedException(Long orderId) {
        super("Access to order " + orderId + " is denied");
    }
}
