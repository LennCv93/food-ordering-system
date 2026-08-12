package com.foodorder.order.application.usecase;

import com.foodorder.order.domain.exception.OrderAccessDeniedException;
import com.foodorder.order.domain.exception.OrderNotFoundException;
import com.foodorder.order.domain.model.Order;
import com.foodorder.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderUseCase {

    private final OrderRepository orderRepository;

    public Order execute(Long orderId, Long requesterId, boolean isAdmin) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        if (!isAdmin && !order.getUserId().equals(requesterId)) {
            throw new OrderAccessDeniedException(orderId);
        }
        return order;
    }
}
