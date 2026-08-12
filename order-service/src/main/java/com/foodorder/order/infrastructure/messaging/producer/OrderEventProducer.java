package com.foodorder.order.infrastructure.messaging.producer;

import com.foodorder.order.application.dto.OrderCreatedPayload;
import com.foodorder.order.application.port.OrderEventPublisherPort;
import com.foodorder.order.infrastructure.messaging.event.EventEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventProducer implements OrderEventPublisherPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishOrderCreated(OrderCreatedPayload payload) {
        kafkaTemplate.send("order.created", payload.orderId().toString(), EventEnvelope.of("order.created", payload));
    }
}
