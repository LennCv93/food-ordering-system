package com.foodorder.order.application.dto;

public record OrderItemRequest(Long productId, int quantity) {
}
