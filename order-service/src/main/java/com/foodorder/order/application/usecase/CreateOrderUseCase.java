package com.foodorder.order.application.usecase;

import com.foodorder.order.application.dto.CreateOrderCommand;
import com.foodorder.order.application.dto.OrderCreatedPayload;
import com.foodorder.order.application.dto.OrderItemRequest;
import com.foodorder.order.application.dto.ProductSnapshot;
import com.foodorder.order.application.port.OrderEventPublisherPort;
import com.foodorder.order.application.port.ProductCatalogPort;
import com.foodorder.order.domain.exception.ProductNotAvailableException;
import com.foodorder.order.domain.model.Order;
import com.foodorder.order.domain.model.OrderItem;
import com.foodorder.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final ProductCatalogPort productCatalogPort;
    private final OrderRepository orderRepository;
    private final OrderEventPublisherPort orderEventPublisherPort;

    @Transactional
    public Order execute(CreateOrderCommand command) {
        List<OrderItem> items = command.items().stream()
                .map(item -> toOrderItem(item, command.authorizationHeader()))
                .toList();
        BigDecimal totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.createNew(command.userId(), command.deliveryAddress(), totalAmount, items);
        order.markPendingPayment();
        Order saved = orderRepository.save(order);

        orderEventPublisherPort.publishOrderCreated(
                new OrderCreatedPayload(saved.getId(), saved.getUserId(), saved.getTotalAmount(), saved.getDeliveryAddress()));

        return saved;
    }

    private OrderItem toOrderItem(OrderItemRequest item, String authorizationHeader) {
        ProductSnapshot product = productCatalogPort.getProduct(item.productId(), authorizationHeader);
        if (!product.available()) {
            throw new ProductNotAvailableException(product.id());
        }
        return OrderItem.create(product.id(), product.name(), product.price(), item.quantity());
    }
}
