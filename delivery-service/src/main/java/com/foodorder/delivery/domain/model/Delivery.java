package com.foodorder.delivery.domain.model;

import com.foodorder.delivery.domain.exception.InvalidDeliveryStateException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class Delivery {

    private final Long id;
    private final Long orderId;
    private final String deliveryAddress;
    private DeliveryStatus status;
    private String courierName;
    private final Instant createdAt;
    private Instant updatedAt;

    public static Delivery createNew(Long orderId, String deliveryAddress) {
        Instant now = Instant.now();
        return new Delivery(null, orderId, deliveryAddress, null, null, now, now);
    }

    public void assign() {
        if (this.status != null) {
            throw new InvalidDeliveryStateException(id, status, DeliveryStatus.ASSIGNED);
        }
        this.status = DeliveryStatus.ASSIGNED;
        this.updatedAt = Instant.now();
    }

    public void startTransit() {
        transition(DeliveryStatus.ASSIGNED, DeliveryStatus.IN_TRANSIT);
    }

    public void markDelivered() {
        transition(DeliveryStatus.IN_TRANSIT, DeliveryStatus.DELIVERED);
    }

    public void markFailed() {
        if (status != DeliveryStatus.ASSIGNED && status != DeliveryStatus.IN_TRANSIT) {
            throw new InvalidDeliveryStateException(id, status, DeliveryStatus.FAILED);
        }
        this.status = DeliveryStatus.FAILED;
        this.updatedAt = Instant.now();
    }

    private void transition(DeliveryStatus expected, DeliveryStatus next) {
        if (this.status != expected) {
            throw new InvalidDeliveryStateException(id, status, next);
        }
        this.status = next;
        this.updatedAt = Instant.now();
    }
}
