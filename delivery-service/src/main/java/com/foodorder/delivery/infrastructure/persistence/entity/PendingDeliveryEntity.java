package com.foodorder.delivery.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pending_deliveries")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PendingDeliveryEntity {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;
}
