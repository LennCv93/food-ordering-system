package com.foodorder.order.application.port;

import com.foodorder.order.application.dto.OrderCreatedPayload;

public interface OrderEventPublisherPort {

    void publishOrderCreated(OrderCreatedPayload payload);
}
