package com.foodorder.delivery.application.usecase;

import com.foodorder.delivery.application.dto.OrderCreatedPayload;
import com.foodorder.delivery.domain.model.PendingDelivery;
import com.foodorder.delivery.domain.repository.PendingDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HandleOrderCreatedUseCase {

    private final PendingDeliveryRepository pendingDeliveryRepository;

    @Transactional
    public void handle(OrderCreatedPayload payload) {
        if (pendingDeliveryRepository.existsByOrderId(payload.orderId())) {
            return;
        }
        pendingDeliveryRepository.save(new PendingDelivery(payload.orderId(), payload.deliveryAddress()));
    }
}
