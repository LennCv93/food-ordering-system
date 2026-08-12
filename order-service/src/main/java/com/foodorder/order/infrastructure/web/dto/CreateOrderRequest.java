package com.foodorder.order.infrastructure.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank String deliveryAddress,
        @NotEmpty @Valid List<CreateOrderItemRequest> items
) {
}
