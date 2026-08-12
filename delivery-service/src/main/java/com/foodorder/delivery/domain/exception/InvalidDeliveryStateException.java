package com.foodorder.delivery.domain.exception;

import com.foodorder.delivery.domain.model.DeliveryStatus;

public class InvalidDeliveryStateException extends RuntimeException {

    public InvalidDeliveryStateException(Long deliveryId, DeliveryStatus currentStatus, DeliveryStatus targetStatus) {
        super("Delivery " + deliveryId + " cannot transition from " + currentStatus + " to " + targetStatus);
    }
}
