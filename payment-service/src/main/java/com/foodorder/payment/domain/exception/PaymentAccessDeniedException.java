package com.foodorder.payment.domain.exception;

public class PaymentAccessDeniedException extends RuntimeException {

    public PaymentAccessDeniedException(Long paymentId) {
        super("Access to payment " + paymentId + " is denied");
    }
}
