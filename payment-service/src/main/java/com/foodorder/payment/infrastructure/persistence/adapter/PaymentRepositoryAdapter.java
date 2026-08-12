package com.foodorder.payment.infrastructure.persistence.adapter;

import com.foodorder.payment.domain.model.Payment;
import com.foodorder.payment.domain.model.PaymentStatus;
import com.foodorder.payment.domain.repository.PaymentRepository;
import com.foodorder.payment.infrastructure.persistence.entity.PaymentEntity;
import com.foodorder.payment.infrastructure.persistence.repository.SpringDataPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final SpringDataPaymentRepository springDataPaymentRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = toEntity(payment);
        PaymentEntity saved = springDataPaymentRepository.save(entity);
        return toDomain(saved, payment.getReason());
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return springDataPaymentRepository.findById(id).map(entity -> toDomain(entity, null));
    }

    @Override
    public Optional<Payment> findByOrderId(Long orderId) {
        return springDataPaymentRepository.findByOrderId(orderId).map(entity -> toDomain(entity, null));
    }

    private PaymentEntity toEntity(Payment payment) {
        return new PaymentEntity(payment.getId(), payment.getOrderId(), payment.getUserId(), payment.getAmount(),
                payment.getStatus().name(), payment.getPaymentMethod(), payment.getTransactionReference(),
                payment.getCreatedAt(), payment.getUpdatedAt());
    }

    private Payment toDomain(PaymentEntity entity, String reason) {
        return new Payment(entity.getId(), entity.getOrderId(), entity.getUserId(), entity.getAmount(),
                PaymentStatus.valueOf(entity.getStatus()), entity.getPaymentMethod(), entity.getTransactionReference(),
                reason, entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
