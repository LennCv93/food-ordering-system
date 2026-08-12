package com.foodorder.order.application.usecase;

import com.foodorder.order.domain.model.Order;
import com.foodorder.order.domain.model.PageResult;
import com.foodorder.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListOrdersUseCase {

    private final OrderRepository orderRepository;

    public PageResult<Order> execute(Long requesterId, boolean isAdmin, int page, int size) {
        return isAdmin ? orderRepository.findAll(page, size) : orderRepository.findByUserId(requesterId, page, size);
    }
}
