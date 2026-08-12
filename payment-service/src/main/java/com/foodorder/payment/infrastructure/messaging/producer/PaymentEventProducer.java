package com.foodorder.payment.infrastructure.messaging.producer;

import com.foodorder.payment.application.dto.PaymentCompletedPayload;
import com.foodorder.payment.application.dto.PaymentFailedPayload;
import com.foodorder.payment.application.port.PaymentEventPublisherPort;
import com.foodorder.payment.infrastructure.messaging.event.EventEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer implements PaymentEventPublisherPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishPaymentCompleted(PaymentCompletedPayload payload) {
        kafkaTemplate.send("payment.completed", payload.orderId().toString(),
                EventEnvelope.of("payment.completed", payload));
    }

    @Override
    public void publishPaymentFailed(PaymentFailedPayload payload) {
        kafkaTemplate.send("payment.failed", payload.orderId().toString(),
                EventEnvelope.of("payment.failed", payload));
    }
}
