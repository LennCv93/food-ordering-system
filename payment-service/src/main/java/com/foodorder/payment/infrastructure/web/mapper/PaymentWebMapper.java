package com.foodorder.payment.infrastructure.web.mapper;

import com.foodorder.payment.domain.model.Payment;
import com.foodorder.payment.infrastructure.web.dto.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentWebMapper {

    public PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getOrderId(), payment.getStatus(), payment.getAmount(),
                payment.getPaymentMethod(), payment.getTransactionReference());
    }
}
