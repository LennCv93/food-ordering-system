package com.foodorder.payment.application.dto;

public record PaymentFailedPayload(Long orderId, Long paymentId, String reason) {
}
