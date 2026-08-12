package com.foodorder.order.application.dto;

import java.math.BigDecimal;

public record OrderCreatedPayload(Long orderId, Long userId, BigDecimal totalAmount, String deliveryAddress) {
}
