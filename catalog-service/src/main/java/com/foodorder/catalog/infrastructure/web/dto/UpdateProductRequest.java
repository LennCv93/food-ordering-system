package com.foodorder.catalog.infrastructure.web.dto;

import com.foodorder.catalog.domain.model.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotBlank String name,
        String description,
        @NotNull @Positive BigDecimal price,
        @NotNull ProductCategory category,
        boolean available,
        @PositiveOrZero int stockQuantity
) {
}
