package com.foodorder.delivery.infrastructure.messaging.producer;

import com.foodorder.delivery.application.dto.DeliveryStatusChangedPayload;
import com.foodorder.delivery.application.port.DeliveryEventPublisherPort;
import com.foodorder.delivery.infrastructure.messaging.event.EventEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryEventProducer implements DeliveryEventPublisherPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishDeliveryStatusChanged(DeliveryStatusChangedPayload payload) {
        kafkaTemplate.send("delivery.status-changed", payload.orderId().toString(),
                EventEnvelope.of("delivery.status-changed", payload));
    }
}
