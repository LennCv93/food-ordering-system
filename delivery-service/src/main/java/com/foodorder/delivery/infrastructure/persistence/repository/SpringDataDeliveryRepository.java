package com.foodorder.delivery.infrastructure.persistence.repository;

import com.foodorder.delivery.infrastructure.persistence.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataDeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    Optional<DeliveryEntity> findByOrderId(Long orderId);
}
