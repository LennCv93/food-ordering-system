package com.foodorder.catalog.application.usecase;

import com.foodorder.catalog.application.dto.UpdateProductCommand;
import com.foodorder.catalog.domain.exception.ProductNotFoundException;
import com.foodorder.catalog.domain.model.Product;
import com.foodorder.catalog.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCase {

    private final ProductRepository productRepository;

    @Transactional
    public Product execute(Long id, UpdateProductCommand command) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        Product updated = existing.withUpdatedFields(command.name(), command.description(), command.price(),
                command.category(), command.available(), command.stockQuantity());
        return productRepository.save(updated);
    }
}
