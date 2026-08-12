package com.foodorder.catalog.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class Product {

    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final ProductCategory category;
    private final boolean available;
    private final int stockQuantity;
    private final Instant createdAt;
    private final Instant updatedAt;

    public static Product createNew(String name, String description, BigDecimal price,
                                     ProductCategory category, int stockQuantity) {
        Instant now = Instant.now();
        return new Product(null, name, description, price, category, true, stockQuantity, now, now);
    }

    public Product withUpdatedFields(String name, String description, BigDecimal price, ProductCategory category,
                                      boolean available, int stockQuantity) {
        return new Product(this.id, name, description, price, category, available, stockQuantity,
                this.createdAt, Instant.now());
    }
}
