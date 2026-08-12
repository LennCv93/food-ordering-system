package com.foodorder.payment.application.usecase;

import com.foodorder.payment.application.dto.OrderCreatedPayload;
import com.foodorder.payment.domain.model.Payment;
import com.foodorder.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HandleOrderCreatedUseCase {

    private final PaymentRepository paymentRepository;

    @Transactional
    public void handle(OrderCreatedPayload payload) {
        if (paymentRepository.findByOrderId(payload.orderId()).isPresent()) {
            return;
        }
        Payment payment = Payment.createNew(payload.orderId(), payload.userId(), payload.totalAmount());
        paymentRepository.save(payment);
    }
}
