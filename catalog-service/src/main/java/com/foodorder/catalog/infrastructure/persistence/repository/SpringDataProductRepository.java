package com.foodorder.catalog.infrastructure.persistence.repository;

import com.foodorder.catalog.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByCategory(String category, Pageable pageable);

    Page<ProductEntity> findByAvailable(boolean available, Pageable pageable);

    Page<ProductEntity> findByCategoryAndAvailable(String category, boolean available, Pageable pageable);
}
