package com.foodorder.delivery.infrastructure.persistence.adapter;

import com.foodorder.delivery.domain.model.PendingDelivery;
import com.foodorder.delivery.domain.repository.PendingDeliveryRepository;
import com.foodorder.delivery.infrastructure.persistence.entity.PendingDeliveryEntity;
import com.foodorder.delivery.infrastructure.persistence.repository.SpringDataPendingDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PendingDeliveryRepositoryAdapter implements PendingDeliveryRepository {

    private final SpringDataPendingDeliveryRepository springDataPendingDeliveryRepository;

    @Override
    public void save(PendingDelivery pendingDelivery) {
        springDataPendingDeliveryRepository.save(
                new PendingDeliveryEntity(pendingDelivery.orderId(), pendingDelivery.deliveryAddress()));
    }

    @Override
    public Optional<PendingDelivery> findByOrderId(Long orderId) {
        return springDataPendingDeliveryRepository.findById(orderId)
                .map(entity -> new PendingDelivery(entity.getOrderId(), entity.getDeliveryAddress()));
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        springDataPendingDeliveryRepository.deleteById(orderId);
    }

    @Override
    public boolean existsByOrderId(Long orderId) {
        return springDataPendingDeliveryRepository.existsById(orderId);
    }
}
