package com.foodorder.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderItem {

    private final Long id;
    private final Long productId;
    private final String productName;
    private final BigDecimal unitPrice;
    private final int quantity;
    private final BigDecimal subtotal;

    public static OrderItem create(Long productId, String productName, BigDecimal unitPrice, int quantity) {
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        return new OrderItem(null, productId, productName, unitPrice, quantity, subtotal);
    }
}
