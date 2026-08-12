package com.foodorder.delivery.domain.repository;

import com.foodorder.delivery.domain.model.PendingDelivery;

import java.util.Optional;

public interface PendingDeliveryRepository {

    void save(PendingDelivery pendingDelivery);

    Optional<PendingDelivery> findByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);

    boolean existsByOrderId(Long orderId);
}
