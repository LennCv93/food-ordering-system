package com.foodorder.payment.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record PayOrderRequest(@NotBlank String paymentMethod) {
}
