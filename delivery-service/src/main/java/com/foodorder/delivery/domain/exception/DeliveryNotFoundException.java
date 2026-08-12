package com.foodorder.delivery.domain.exception;

public class DeliveryNotFoundException extends RuntimeException {

    private DeliveryNotFoundException(String message) {
        super(message);
    }

    public static DeliveryNotFoundException forId(Long id) {
        return new DeliveryNotFoundException("Delivery " + id + " not found");
    }

    public static DeliveryNotFoundException forOrderId(Long orderId) {
        return new DeliveryNotFoundException("Delivery for order " + orderId + " not found");
    }
}
