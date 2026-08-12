package com.foodorder.catalog.domain.repository;

import com.foodorder.catalog.domain.model.ProductCategory;

public record ProductFilter(ProductCategory category, Boolean available) {
}
