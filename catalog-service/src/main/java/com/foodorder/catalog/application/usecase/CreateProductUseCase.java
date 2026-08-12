package com.foodorder.catalog.application.usecase;

import com.foodorder.catalog.application.dto.CreateProductCommand;
import com.foodorder.catalog.domain.model.Product;
import com.foodorder.catalog.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductRepository productRepository;

    @Transactional
    public Product execute(CreateProductCommand command) {
        Product product = Product.createNew(command.name(), command.description(), command.price(),
                command.category(), command.stockQuantity());
        return productRepository.save(product);
    }
}
