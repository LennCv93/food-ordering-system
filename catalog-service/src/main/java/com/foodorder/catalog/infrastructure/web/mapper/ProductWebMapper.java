package com.foodorder.catalog.infrastructure.web.mapper;

import com.foodorder.catalog.domain.model.PageResult;
import com.foodorder.catalog.domain.model.Product;
import com.foodorder.catalog.infrastructure.web.dto.PageResponse;
import com.foodorder.catalog.infrastructure.web.dto.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductWebMapper {

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getCategory(), product.isAvailable(), product.getStockQuantity());
    }

    public PageResponse<ProductResponse> toPageResponse(PageResult<Product> pageResult) {
        return new PageResponse<>(
                pageResult.content().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements(),
                pageResult.totalPages()
        );
    }
}
