package com.foodorder.delivery.infrastructure.web.mapper;

import com.foodorder.delivery.domain.model.Delivery;
import com.foodorder.delivery.infrastructure.web.dto.DeliveryResponse;
import org.springframework.stereotype.Component;

@Component
public class DeliveryWebMapper {

    public DeliveryResponse toResponse(Delivery delivery) {
        return new DeliveryResponse(delivery.getId(), delivery.getOrderId(), delivery.getDeliveryAddress(),
                delivery.getStatus(), delivery.getCourierName());
    }
}
