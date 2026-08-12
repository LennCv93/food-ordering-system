package com.foodorder.payment.infrastructure.messaging.consumer;

import com.foodorder.payment.application.dto.OrderCreatedPayload;
import com.foodorder.payment.application.usecase.HandleOrderCreatedUseCase;
import com.foodorder.payment.infrastructure.messaging.event.EventEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final HandleOrderCreatedUseCase handleOrderCreatedUseCase;

    @KafkaListener(topics = "order.created", groupId = "payment-service-group")
    public void onOrderCreated(EventEnvelope<OrderCreatedPayload> event) {
        handleOrderCreatedUseCase.handle(event.payload());
    }
}
