package com.foodorder.catalog.infrastructure.persistence.adapter;

import com.foodorder.catalog.domain.model.PageResult;
import com.foodorder.catalog.domain.model.Product;
import com.foodorder.catalog.domain.model.ProductCategory;
import com.foodorder.catalog.domain.repository.ProductFilter;
import com.foodorder.catalog.domain.repository.ProductRepository;
import com.foodorder.catalog.infrastructure.persistence.entity.ProductEntity;
import com.foodorder.catalog.infrastructure.persistence.repository.SpringDataProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductRepository springDataProductRepository;

    @Override
    public Product save(Product product) {
        ProductEntity entity = toEntity(product);
        ProductEntity saved = springDataProductRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springDataProductRepository.findById(id).map(this::toDomain);
    }

    @Override
    public PageResult<Product> findAll(ProductFilter filter, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        String category = filter.category() != null ? filter.category().name() : null;
        Boolean available = filter.available();

        Page<ProductEntity> result;
        if (category != null && available != null) {
            result = springDataProductRepository.findByCategoryAndAvailable(category, available, pageRequest);
        } else if (category != null) {
            result = springDataProductRepository.findByCategory(category, pageRequest);
        } else if (available != null) {
            result = springDataProductRepository.findByAvailable(available, pageRequest);
        } else {
            result = springDataProductRepository.findAll(pageRequest);
        }

        List<Product> content = result.getContent().stream().map(this::toDomain).toList();
        return new PageResult<>(content, page, size, result.getTotalElements());
    }

    private ProductEntity toEntity(Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getCategory().name(), product.isAvailable(), product.getStockQuantity(),
                product.getCreatedAt(), product.getUpdatedAt());
    }

    private Product toDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(),
                ProductCategory.valueOf(entity.getCategory()), entity.isAvailable(), entity.getStockQuantity(),
                entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
