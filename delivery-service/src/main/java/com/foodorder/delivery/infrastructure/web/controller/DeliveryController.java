package com.foodorder.delivery.infrastructure.web.controller;

import com.foodorder.delivery.application.usecase.GetDeliveryByOrderUseCase;
import com.foodorder.delivery.application.usecase.UpdateDeliveryStatusUseCase;
import com.foodorder.delivery.infrastructure.web.dto.DeliveryResponse;
import com.foodorder.delivery.infrastructure.web.dto.UpdateDeliveryStatusRequest;
import com.foodorder.delivery.infrastructure.web.mapper.DeliveryWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final GetDeliveryByOrderUseCase getDeliveryByOrderUseCase;
    private final UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase;
    private final DeliveryWebMapper deliveryWebMapper;

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public DeliveryResponse getByOrder(@PathVariable Long orderId) {
        return deliveryWebMapper.toResponse(getDeliveryByOrderUseCase.execute(orderId));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public DeliveryResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateDeliveryStatusRequest request) {
        return deliveryWebMapper.toResponse(updateDeliveryStatusUseCase.execute(id, request.status()));
    }
}
