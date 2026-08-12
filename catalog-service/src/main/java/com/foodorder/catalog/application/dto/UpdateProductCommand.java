package com.foodorder.catalog.application.dto;

import com.foodorder.catalog.domain.model.ProductCategory;

import java.math.BigDecimal;

public record UpdateProductCommand(String name, String description, BigDecimal price, ProductCategory category,
                                    boolean available, int stockQuantity) {
}
