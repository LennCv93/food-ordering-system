package com.foodorder.order.application.usecase;

import com.foodorder.order.application.dto.PaymentFailedPayload;
import com.foodorder.order.domain.exception.OrderNotFoundException;
import com.foodorder.order.domain.model.Order;
import com.foodorder.order.domain.model.OrderStatus;
import com.foodorder.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HandlePaymentFailedUseCase {

    private final OrderRepository orderRepository;

    @Transactional
    public void handle(PaymentFailedPayload payload) {
        Order order = orderRepository.findById(payload.orderId())
                .orElseThrow(() -> new OrderNotFoundException(payload.orderId()));
        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            return;
        }
        order.markPaymentFailed();
        orderRepository.save(order);
    }
}
