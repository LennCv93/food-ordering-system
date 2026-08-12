package com.foodorder.delivery.application.usecase;

import com.foodorder.delivery.application.dto.DeliveryStatusChangedPayload;
import com.foodorder.delivery.application.port.DeliveryEventPublisherPort;
import com.foodorder.delivery.domain.exception.DeliveryNotFoundException;
import com.foodorder.delivery.domain.exception.InvalidDeliveryStateException;
import com.foodorder.delivery.domain.model.Delivery;
import com.foodorder.delivery.domain.model.DeliveryStatus;
import com.foodorder.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateDeliveryStatusUseCase {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryEventPublisherPort deliveryEventPublisherPort;

    @Transactional
    public Delivery execute(Long deliveryId, DeliveryStatus targetStatus) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> DeliveryNotFoundException.forId(deliveryId));

        switch (targetStatus) {
            case IN_TRANSIT -> delivery.startTransit();
            case DELIVERED -> delivery.markDelivered();
            case FAILED -> delivery.markFailed();
            default -> throw new InvalidDeliveryStateException(deliveryId, delivery.getStatus(), targetStatus);
        }

        Delivery saved = deliveryRepository.save(delivery);
        deliveryEventPublisherPort.publishDeliveryStatusChanged(
                new DeliveryStatusChangedPayload(saved.getOrderId(), saved.getId(), saved.getStatus().name()));
        return saved;
    }
}
