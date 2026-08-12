package com.foodorder.payment.application.port;

import com.foodorder.payment.application.dto.PaymentCompletedPayload;
import com.foodorder.payment.application.dto.PaymentFailedPayload;

public interface PaymentEventPublisherPort {

    void publishPaymentCompleted(PaymentCompletedPayload payload);

    void publishPaymentFailed(PaymentFailedPayload payload);
}
