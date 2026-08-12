package com.foodorder.delivery.infrastructure.web.dto;

import com.foodorder.delivery.domain.model.DeliveryStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateDeliveryStatusRequest(@NotNull DeliveryStatus status) {
}
