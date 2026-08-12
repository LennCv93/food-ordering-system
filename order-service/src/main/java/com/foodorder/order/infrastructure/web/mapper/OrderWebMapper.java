package com.foodorder.order.infrastructure.web.mapper;

import com.foodorder.order.domain.model.Order;
import com.foodorder.order.domain.model.OrderItem;
import com.foodorder.order.domain.model.PageResult;
import com.foodorder.order.infrastructure.web.dto.OrderItemResponse;
import com.foodorder.order.infrastructure.web.dto.OrderResponse;
import com.foodorder.order.infrastructure.web.dto.PageResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderWebMapper {

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(order.getId(), order.getStatus(), order.getTotalAmount(), order.getDeliveryAddress(),
                order.getItems().stream().map(this::toItemResponse).toList());
    }

    public PageResponse<OrderResponse> toPageResponse(PageResult<Order> pageResult) {
        return new PageResponse<>(
                pageResult.content().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements(),
                pageResult.totalPages()
        );
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        return new OrderItemResponse(item.getId(), item.getProductId(), item.getProductName(), item.getUnitPrice(),
                item.getQuantity(), item.getSubtotal());
    }
}
