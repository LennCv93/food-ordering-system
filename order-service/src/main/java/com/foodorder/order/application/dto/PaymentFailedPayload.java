package com.foodorder.order.application.dto;

public record PaymentFailedPayload(Long orderId, Long paymentId, String reason) {
}
