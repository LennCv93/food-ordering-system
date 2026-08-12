package com.foodorder.order.domain.model;

import com.foodorder.order.domain.exception.InvalidOrderStateException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
public class Order {

    private final Long id;
    private final Long userId;
    private OrderStatus status;
    private final String deliveryAddress;
    private final BigDecimal totalAmount;
    private final List<OrderItem> items;
    private final Instant createdAt;
    private Instant updatedAt;

    public static Order createNew(Long userId, String deliveryAddress, BigDecimal totalAmount, List<OrderItem> items) {
        Instant now = Instant.now();
        return new Order(null, userId, OrderStatus.CREATED, deliveryAddress, totalAmount, items, now, now);
    }

    public void markPendingPayment() {
        transition(OrderStatus.CREATED, OrderStatus.PENDING_PAYMENT);
    }

    public void markPaid() {
        transition(OrderStatus.PENDING_PAYMENT, OrderStatus.PAID);
    }

    public void markPaymentFailed() {
        transition(OrderStatus.PENDING_PAYMENT, OrderStatus.PAYMENT_FAILED);
    }

    public void markPreparing() {
        transition(OrderStatus.PAID, OrderStatus.PREPARING);
    }

    public void markInDelivery() {
        transition(OrderStatus.PREPARING, OrderStatus.IN_DELIVERY);
    }

    public void markDelivered() {
        transition(OrderStatus.IN_DELIVERY, OrderStatus.DELIVERED);
    }

    public void cancel() {
        if (status != OrderStatus.CREATED && status != OrderStatus.PENDING_PAYMENT) {
            throw new InvalidOrderStateException(id, status);
        }
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = Instant.now();
    }

    private void transition(OrderStatus expected, OrderStatus next) {
        if (this.status != expected) {
            throw new InvalidOrderStateException(id, status, next);
        }
        this.status = next;
        this.updatedAt = Instant.now();
    }
}
