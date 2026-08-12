package com.foodorder.order.domain.exception;

import com.foodorder.order.domain.model.OrderStatus;

public class InvalidOrderStateException extends RuntimeException {

    public InvalidOrderStateException(Long orderId, OrderStatus currentStatus, OrderStatus targetStatus) {
        super("Order " + orderId + " cannot transition from " + currentStatus + " to " + targetStatus);
    }

    public InvalidOrderStateException(Long orderId, OrderStatus currentStatus) {
        super("Order " + orderId + " cannot be cancelled from status " + currentStatus);
    }
}
