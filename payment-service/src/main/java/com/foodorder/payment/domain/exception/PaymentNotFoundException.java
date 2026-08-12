package com.foodorder.payment.domain.exception;

public class PaymentNotFoundException extends RuntimeException {

    private PaymentNotFoundException(String message) {
        super(message);
    }

    public static PaymentNotFoundException forId(Long id) {
        return new PaymentNotFoundException("Payment " + id + " not found");
    }

    public static PaymentNotFoundException forOrderId(Long orderId) {
        return new PaymentNotFoundException("Payment for order " + orderId + " not found");
    }
}
