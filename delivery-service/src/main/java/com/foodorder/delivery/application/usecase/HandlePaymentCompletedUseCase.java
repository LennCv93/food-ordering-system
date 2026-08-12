package com.foodorder.delivery.application.usecase;

import com.foodorder.delivery.application.dto.DeliveryStatusChangedPayload;
import com.foodorder.delivery.application.dto.PaymentCompletedPayload;
import com.foodorder.delivery.application.port.DeliveryEventPublisherPort;
import com.foodorder.delivery.domain.model.Delivery;
import com.foodorder.delivery.domain.model.PendingDelivery;
import com.foodorder.delivery.domain.repository.DeliveryRepository;
import com.foodorder.delivery.domain.repository.PendingDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HandlePaymentCompletedUseCase {

    private final PendingDeliveryRepository pendingDeliveryRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryEventPublisherPort deliveryEventPublisherPort;

    @Transactional
    public void handle(PaymentCompletedPayload payload) {
        if (deliveryRepository.findByOrderId(payload.orderId()).isPresent()) {
            return;
        }

        PendingDelivery pendingDelivery = pendingDeliveryRepository.findByOrderId(payload.orderId())
                .orElseThrow(() -> new IllegalStateException(
                        "No pending delivery address found for order " + payload.orderId()));

        Delivery delivery = Delivery.createNew(payload.orderId(), pendingDelivery.deliveryAddress());
        delivery.assign();
        Delivery saved = deliveryRepository.save(delivery);
        pendingDeliveryRepository.deleteByOrderId(payload.orderId());

        deliveryEventPublisherPort.publishDeliveryStatusChanged(
                new DeliveryStatusChangedPayload(saved.getOrderId(), saved.getId(), saved.getStatus().name()));
    }
}
