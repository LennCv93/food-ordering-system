package com.foodorder.delivery.application.port;

import com.foodorder.delivery.application.dto.DeliveryStatusChangedPayload;

public interface DeliveryEventPublisherPort {

    void publishDeliveryStatusChanged(DeliveryStatusChangedPayload payload);
}
