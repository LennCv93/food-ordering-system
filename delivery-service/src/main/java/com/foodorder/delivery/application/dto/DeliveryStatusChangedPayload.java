package com.foodorder.delivery.application.dto;

public record DeliveryStatusChangedPayload(Long orderId, Long deliveryId, String status) {
}
