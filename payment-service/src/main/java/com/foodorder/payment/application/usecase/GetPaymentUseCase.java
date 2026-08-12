package com.foodorder.payment.application.usecase;

import com.foodorder.payment.domain.exception.PaymentAccessDeniedException;
import com.foodorder.payment.domain.exception.PaymentNotFoundException;
import com.foodorder.payment.domain.model.Payment;
import com.foodorder.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPaymentUseCase {

    private final PaymentRepository paymentRepository;

    public Payment execute(Long id, Long requesterId, boolean isAdmin) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> PaymentNotFoundException.forId(id));
        if (!isAdmin && !payment.getUserId().equals(requesterId)) {
            throw new PaymentAccessDeniedException(id);
        }
        return payment;
    }
}
