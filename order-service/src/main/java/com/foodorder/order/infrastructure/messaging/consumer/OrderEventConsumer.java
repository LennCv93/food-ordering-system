package com.foodorder.order.infrastructure.messaging.consumer;

import com.foodorder.order.application.dto.DeliveryStatusChangedPayload;
import com.foodorder.order.application.dto.PaymentCompletedPayload;
import com.foodorder.order.application.dto.PaymentFailedPayload;
import com.foodorder.order.application.usecase.HandleDeliveryStatusChangedUseCase;
import com.foodorder.order.application.usecase.HandlePaymentCompletedUseCase;
import com.foodorder.order.application.usecase.HandlePaymentFailedUseCase;
import com.foodorder.order.infrastructure.messaging.event.EventEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final HandlePaymentCompletedUseCase handlePaymentCompletedUseCase;
    private final HandlePaymentFailedUseCase handlePaymentFailedUseCase;
    private final HandleDeliveryStatusChangedUseCase handleDeliveryStatusChangedUseCase;

    @KafkaListener(topics = "payment.completed", groupId = "order-service-group")
    public void onPaymentCompleted(EventEnvelope<PaymentCompletedPayload> event) {
        handlePaymentCompletedUseCase.handle(event.payload());
    }

    @KafkaListener(topics = "payment.failed", groupId = "order-service-group")
    public void onPaymentFailed(EventEnvelope<PaymentFailedPayload> event) {
        handlePaymentFailedUseCase.handle(event.payload());
    }

    @KafkaListener(topics = "delivery.status-changed", groupId = "order-service-group")
    public void onDeliveryStatusChanged(EventEnvelope<DeliveryStatusChangedPayload> event) {
        handleDeliveryStatusChangedUseCase.handle(event.payload());
    }
}
