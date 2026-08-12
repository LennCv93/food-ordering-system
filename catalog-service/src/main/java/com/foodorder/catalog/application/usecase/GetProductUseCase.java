package com.foodorder.catalog.application.usecase;

import com.foodorder.catalog.domain.exception.ProductNotFoundException;
import com.foodorder.catalog.domain.model.Product;
import com.foodorder.catalog.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProductUseCase {

    private final ProductRepository productRepository;

    public Product execute(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
