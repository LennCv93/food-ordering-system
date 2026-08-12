package com.foodorder.catalog.application.usecase;

import com.foodorder.catalog.domain.model.PageResult;
import com.foodorder.catalog.domain.model.Product;
import com.foodorder.catalog.domain.repository.ProductFilter;
import com.foodorder.catalog.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListProductsUseCase {

    private final ProductRepository productRepository;

    public PageResult<Product> execute(ProductFilter filter, int page, int size) {
        return productRepository.findAll(filter, page, size);
    }
}
