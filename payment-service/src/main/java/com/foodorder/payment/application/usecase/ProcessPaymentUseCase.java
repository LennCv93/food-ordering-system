package com.foodorder.payment.application.usecase;

import com.foodorder.payment.application.dto.PaymentCompletedPayload;
import com.foodorder.payment.application.dto.PaymentFailedPayload;
import com.foodorder.payment.application.port.PaymentEventPublisherPort;
import com.foodorder.payment.domain.exception.PaymentAccessDeniedException;
import com.foodorder.payment.domain.exception.PaymentAlreadyResolvedException;
import com.foodorder.payment.domain.exception.PaymentNotFoundException;
import com.foodorder.payment.domain.model.Payment;
import com.foodorder.payment.domain.model.PaymentStatus;
import com.foodorder.payment.domain.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class ProcessPaymentUseCase {

    private static final String REFERENCE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisherPort paymentEventPublisherPort;
    private final double successRate;
    private final Random random = new Random();

    public ProcessPaymentUseCase(PaymentRepository paymentRepository,
                                  PaymentEventPublisherPort paymentEventPublisherPort,
                                  @Value("${payment.simulation.success-rate}") double successRate) {
        this.paymentRepository = paymentRepository;
        this.paymentEventPublisherPort = paymentEventPublisherPort;
        this.successRate = successRate;
    }

    @Transactional
    public Payment execute(Long orderId, String paymentMethod, Long requesterId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> PaymentNotFoundException.forOrderId(orderId));
        if (!payment.getUserId().equals(requesterId)) {
            throw new PaymentAccessDeniedException(payment.getId());
        }
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new PaymentAlreadyResolvedException(orderId);
        }

        boolean success = random.nextDouble() < successRate;
        if (success) {
            payment.approve(paymentMethod, generateTransactionReference());
        } else {
            payment.reject(paymentMethod, "Fondos insuficientes");
        }
        Payment saved = paymentRepository.save(payment);

        if (success) {
            paymentEventPublisherPort.publishPaymentCompleted(
                    new PaymentCompletedPayload(saved.getOrderId(), saved.getId(), saved.getAmount()));
        } else {
            paymentEventPublisherPort.publishPaymentFailed(
                    new PaymentFailedPayload(saved.getOrderId(), saved.getId(), saved.getReason()));
        }

        return saved;
    }

    private String generateTransactionReference() {
        StringBuilder sb = new StringBuilder("TXN-");
        for (int i = 0; i < 8; i++) {
            sb.append(REFERENCE_CHARS.charAt(random.nextInt(REFERENCE_CHARS.length())));
        }
        return sb.toString();
    }
}
