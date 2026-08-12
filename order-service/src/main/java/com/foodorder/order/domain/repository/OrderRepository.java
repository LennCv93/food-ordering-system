package com.foodorder.order.domain.repository;

import com.foodorder.order.domain.model.Order;
import com.foodorder.order.domain.model.PageResult;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);

    PageResult<Order> findByUserId(Long userId, int page, int size);

    PageResult<Order> findAll(int page, int size);
}
