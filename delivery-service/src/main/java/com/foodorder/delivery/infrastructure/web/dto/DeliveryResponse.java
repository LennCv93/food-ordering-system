package com.foodorder.delivery.infrastructure.web.dto;

import com.foodorder.delivery.domain.model.DeliveryStatus;

public record DeliveryResponse(Long id, Long orderId, String deliveryAddress, DeliveryStatus status, String courierName) {
}
