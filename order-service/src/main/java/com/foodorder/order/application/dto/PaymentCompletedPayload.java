package com.foodorder.order.application.dto;

import java.math.BigDecimal;

public record PaymentCompletedPayload(Long orderId, Long paymentId, BigDecimal amount) {
}
