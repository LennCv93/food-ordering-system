package com.foodorder.delivery.infrastructure.messaging.event;

import java.time.Instant;
import java.util.UUID;

public record EventEnvelope<T>(String eventId, String eventType, Instant occurredAt, T payload) {

    public static <T> EventEnvelope<T> of(String eventType, T payload) {
        return new EventEnvelope<>(UUID.randomUUID().toString(), eventType, Instant.now(), payload);
    }
}
