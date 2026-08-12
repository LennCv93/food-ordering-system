package com.foodorder.delivery.infrastructure.persistence.repository;

import com.foodorder.delivery.infrastructure.persistence.entity.PendingDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPendingDeliveryRepository extends JpaRepository<PendingDeliveryEntity, Long> {
}
