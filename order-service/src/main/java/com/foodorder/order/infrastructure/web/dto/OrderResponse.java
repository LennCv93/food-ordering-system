package com.foodorder.order.infrastructure.web.dto;

import com.foodorder.order.domain.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Long id, OrderStatus status, BigDecimal totalAmount, String deliveryAddress,
                             List<OrderItemResponse> items) {
}
