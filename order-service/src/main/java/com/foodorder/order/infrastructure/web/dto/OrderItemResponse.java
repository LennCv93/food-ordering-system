package com.foodorder.order.infrastructure.web.dto;

import java.math.BigDecimal;

public record OrderItemResponse(Long id, Long productId, String productName, BigDecimal unitPrice,
                                 int quantity, BigDecimal subtotal) {
}
