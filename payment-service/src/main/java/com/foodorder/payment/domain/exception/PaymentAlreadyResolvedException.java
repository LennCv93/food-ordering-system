package com.foodorder.payment.domain.exception;

public class PaymentAlreadyResolvedException extends RuntimeException {

    public PaymentAlreadyResolvedException(Long orderId) {
        super("Payment for order " + orderId + " is already resolved");
    }
}
