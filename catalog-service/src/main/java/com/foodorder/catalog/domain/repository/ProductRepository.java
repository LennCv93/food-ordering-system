package com.foodorder.catalog.domain.repository;

import com.foodorder.catalog.domain.model.PageResult;
import com.foodorder.catalog.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    PageResult<Product> findAll(ProductFilter filter, int page, int size);
}
