package com.foodorder.order.infrastructure.persistence.adapter;

import com.foodorder.order.domain.model.Order;
import com.foodorder.order.domain.model.OrderItem;
import com.foodorder.order.domain.model.OrderStatus;
import com.foodorder.order.domain.model.PageResult;
import com.foodorder.order.domain.repository.OrderRepository;
import com.foodorder.order.infrastructure.persistence.entity.OrderEntity;
import com.foodorder.order.infrastructure.persistence.entity.OrderItemEntity;
import com.foodorder.order.infrastructure.persistence.repository.SpringDataOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository springDataOrderRepository;

    @Override
    public Order save(Order order) {
        OrderEntity entity = toEntity(order);
        OrderEntity saved = springDataOrderRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return springDataOrderRepository.findById(id).map(this::toDomain);
    }

    @Override
    public PageResult<Order> findByUserId(Long userId, int page, int size) {
        Page<OrderEntity> result = springDataOrderRepository.findByUserId(userId, PageRequest.of(page, size));
        List<Order> content = result.getContent().stream().map(this::toDomain).toList();
        return new PageResult<>(content, page, size, result.getTotalElements());
    }

    @Override
    public PageResult<Order> findAll(int page, int size) {
        Page<OrderEntity> result = springDataOrderRepository.findAll(PageRequest.of(page, size));
        List<Order> content = result.getContent().stream().map(this::toDomain).toList();
        return new PageResult<>(content, page, size, result.getTotalElements());
    }

    private OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity(order.getId(), order.getUserId(), order.getStatus().name(),
                order.getDeliveryAddress(), order.getTotalAmount(), new ArrayList<>(), order.getCreatedAt(),
                order.getUpdatedAt());
        List<OrderItemEntity> itemEntities = order.getItems().stream()
                .map(item -> new OrderItemEntity(item.getId(), entity, item.getProductId(), item.getProductName(),
                        item.getUnitPrice(), item.getQuantity(), item.getSubtotal()))
                .toList();
        entity.getItems().addAll(itemEntities);
        return entity;
    }

    private Order toDomain(OrderEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(i -> new OrderItem(i.getId(), i.getProductId(), i.getProductName(), i.getUnitPrice(),
                        i.getQuantity(), i.getSubtotal()))
                .toList();
        return new Order(entity.getId(), entity.getUserId(), OrderStatus.valueOf(entity.getStatus()),
                entity.getDeliveryAddress(), entity.getTotalAmount(), items, entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
