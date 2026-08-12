package com.foodorder.order.application.usecase;

import com.foodorder.order.application.dto.DeliveryStatusChangedPayload;
import com.foodorder.order.domain.exception.OrderNotFoundException;
import com.foodorder.order.domain.model.Order;
import com.foodorder.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HandleDeliveryStatusChangedUseCase {

    private final OrderRepository orderRepository;

    @Transactional
    public void handle(DeliveryStatusChangedPayload payload) {
        Order order = orderRepository.findById(payload.orderId())
                .orElseThrow(() -> new OrderNotFoundException(payload.orderId()));

        switch (payload.status()) {
            case "ASSIGNED" -> order.markPreparing();
            case "IN_TRANSIT" -> order.markInDelivery();
            case "DELIVERED" -> order.markDelivered();
            default -> {
                return;
            }
        }
        orderRepository.save(order);
    }
}
