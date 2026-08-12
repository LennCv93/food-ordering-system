package com.foodorder.order.application.dto;

public record DeliveryStatusChangedPayload(Long orderId, Long deliveryId, String status) {
}
