package com.foodorder.order.application.usecase;

import com.foodorder.order.application.dto.DeliveryStatusChangedPayload;
import com.foodorder.order.domain.exception.OrderNotFoundException;
import com.foodorder.order.domain.model.Order;
import com.foodorder.order.domain.model.OrderStatus;
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
            case "ASSIGNED" -> {
                if (order.getStatus() != OrderStatus.PAID) {
                    return;
                }
                order.markPreparing();
            }
            case "IN_TRANSIT" -> {
                if (order.getStatus() != OrderStatus.PREPARING) {
                    return;
                }
                order.markInDelivery();
            }
            case "DELIVERED" -> {
                if (order.getStatus() != OrderStatus.IN_DELIVERY) {
                    return;
                }
                order.markDelivered();
            }
            default -> {
                return;
            }
        }
        orderRepository.save(order);
    }
}
