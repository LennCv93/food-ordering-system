package com.foodorder.delivery.infrastructure.messaging.consumer;

import com.foodorder.delivery.application.dto.OrderCreatedPayload;
import com.foodorder.delivery.application.dto.PaymentCompletedPayload;
import com.foodorder.delivery.application.usecase.HandleOrderCreatedUseCase;
import com.foodorder.delivery.application.usecase.HandlePaymentCompletedUseCase;
import com.foodorder.delivery.infrastructure.messaging.event.EventEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryEventConsumer {

    private final HandleOrderCreatedUseCase handleOrderCreatedUseCase;
    private final HandlePaymentCompletedUseCase handlePaymentCompletedUseCase;

    @KafkaListener(topics = "order.created", groupId = "delivery-service-group")
    public void onOrderCreated(EventEnvelope<OrderCreatedPayload> event) {
        handleOrderCreatedUseCase.handle(event.payload());
    }

    @KafkaListener(topics = "payment.completed", groupId = "delivery-service-group")
    public void onPaymentCompleted(EventEnvelope<PaymentCompletedPayload> event) {
        handlePaymentCompletedUseCase.handle(event.payload());
    }
}
