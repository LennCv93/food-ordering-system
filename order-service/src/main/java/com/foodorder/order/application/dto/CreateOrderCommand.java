package com.foodorder.order.application.dto;

import java.util.List;

public record CreateOrderCommand(Long userId, String deliveryAddress, List<OrderItemRequest> items,
                                  String authorizationHeader) {
}
