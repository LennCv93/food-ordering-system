package com.foodorder.payment.domain.model;

import com.foodorder.payment.domain.exception.PaymentAlreadyResolvedException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class Payment {

    private final Long id;
    private final Long orderId;
    private final Long userId;
    private final BigDecimal amount;
    private PaymentStatus status;
    private String paymentMethod;
    private String transactionReference;
    private String reason;
    private final Instant createdAt;
    private Instant updatedAt;

    public static Payment createNew(Long orderId, Long userId, BigDecimal amount) {
        Instant now = Instant.now();
        return new Payment(null, orderId, userId, amount, PaymentStatus.PENDING, null, null, null, now, now);
    }

    public void approve(String paymentMethod, String transactionReference) {
        requirePending();
        this.status = PaymentStatus.APPROVED;
        this.paymentMethod = paymentMethod;
        this.transactionReference = transactionReference;
        this.updatedAt = Instant.now();
    }

    public void reject(String paymentMethod, String reason) {
        requirePending();
        this.status = PaymentStatus.REJECTED;
        this.paymentMethod = paymentMethod;
        this.reason = reason;
        this.updatedAt = Instant.now();
    }

    private void requirePending() {
        if (this.status != PaymentStatus.PENDING) {
            throw new PaymentAlreadyResolvedException(orderId);
        }
    }
}
