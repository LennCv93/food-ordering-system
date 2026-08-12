package com.foodorder.payment.domain.repository;

import com.foodorder.payment.domain.model.Payment;

import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(Long id);

    Optional<Payment> findByOrderId(Long orderId);
}
