package com.foodorder.catalog.infrastructure.web.dto;

import com.foodorder.catalog.domain.model.ProductCategory;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price,
                               ProductCategory category, boolean available, int stockQuantity) {
}
