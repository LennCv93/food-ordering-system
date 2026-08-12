package com.foodorder.delivery.application.usecase;

import com.foodorder.delivery.domain.exception.DeliveryNotFoundException;
import com.foodorder.delivery.domain.model.Delivery;
import com.foodorder.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDeliveryByOrderUseCase {

    private final DeliveryRepository deliveryRepository;

    public Delivery execute(Long orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> DeliveryNotFoundException.forOrderId(orderId));
    }
}
