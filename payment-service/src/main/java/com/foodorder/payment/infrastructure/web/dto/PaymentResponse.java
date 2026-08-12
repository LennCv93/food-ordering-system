package com.foodorder.payment.infrastructure.web.dto;

import com.foodorder.payment.domain.model.PaymentStatus;

import java.math.BigDecimal;

public record PaymentResponse(Long id, Long orderId, PaymentStatus status, BigDecimal amount,
                               String paymentMethod, String transactionReference) {
}
