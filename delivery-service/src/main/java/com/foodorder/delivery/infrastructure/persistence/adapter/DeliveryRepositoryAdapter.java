package com.foodorder.delivery.infrastructure.persistence.adapter;

import com.foodorder.delivery.domain.model.Delivery;
import com.foodorder.delivery.domain.model.DeliveryStatus;
import com.foodorder.delivery.domain.repository.DeliveryRepository;
import com.foodorder.delivery.infrastructure.persistence.entity.DeliveryEntity;
import com.foodorder.delivery.infrastructure.persistence.repository.SpringDataDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeliveryRepositoryAdapter implements DeliveryRepository {

    private final SpringDataDeliveryRepository springDataDeliveryRepository;

    @Override
    public Delivery save(Delivery delivery) {
        DeliveryEntity entity = toEntity(delivery);
        DeliveryEntity saved = springDataDeliveryRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Delivery> findById(Long id) {
        return springDataDeliveryRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Delivery> findByOrderId(Long orderId) {
        return springDataDeliveryRepository.findByOrderId(orderId).map(this::toDomain);
    }

    private DeliveryEntity toEntity(Delivery delivery) {
        return new DeliveryEntity(delivery.getId(), delivery.getOrderId(), delivery.getDeliveryAddress(),
                delivery.getStatus().name(), delivery.getCourierName(), delivery.getCreatedAt(), delivery.getUpdatedAt());
    }

    private Delivery toDomain(DeliveryEntity entity) {
        return new Delivery(entity.getId(), entity.getOrderId(), entity.getDeliveryAddress(),
                DeliveryStatus.valueOf(entity.getStatus()), entity.getCourierName(), entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
