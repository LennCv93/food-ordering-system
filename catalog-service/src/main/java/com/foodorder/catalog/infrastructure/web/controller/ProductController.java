package com.foodorder.catalog.infrastructure.web.controller;

import com.foodorder.catalog.application.dto.CreateProductCommand;
import com.foodorder.catalog.application.dto.UpdateProductCommand;
import com.foodorder.catalog.application.usecase.CreateProductUseCase;
import com.foodorder.catalog.application.usecase.GetProductUseCase;
import com.foodorder.catalog.application.usecase.ListProductsUseCase;
import com.foodorder.catalog.application.usecase.UpdateProductUseCase;
import com.foodorder.catalog.domain.model.Product;
import com.foodorder.catalog.domain.model.ProductCategory;
import com.foodorder.catalog.domain.repository.ProductFilter;
import com.foodorder.catalog.infrastructure.web.dto.CreateProductRequest;
import com.foodorder.catalog.infrastructure.web.dto.PageResponse;
import com.foodorder.catalog.infrastructure.web.dto.ProductResponse;
import com.foodorder.catalog.infrastructure.web.dto.UpdateProductRequest;
import com.foodorder.catalog.infrastructure.web.mapper.ProductWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ListProductsUseCase listProductsUseCase;
    private final GetProductUseCase getProductUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final ProductWebMapper productWebMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public PageResponse<ProductResponse> listProducts(@RequestParam(required = false) ProductCategory category,
                                                        @RequestParam(required = false) Boolean available,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        return productWebMapper.toPageResponse(
                listProductsUseCase.execute(new ProductFilter(category, available), page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ProductResponse getById(@PathVariable Long id) {
        return productWebMapper.toResponse(getProductUseCase.execute(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        Product product = createProductUseCase.execute(new CreateProductCommand(
                request.name(), request.description(), request.price(), request.category(), request.stockQuantity()));
        return ResponseEntity.status(HttpStatus.CREATED).body(productWebMapper.toResponse(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        Product product = updateProductUseCase.execute(id, new UpdateProductCommand(
                request.name(), request.description(), request.price(), request.category(),
                request.available(), request.stockQuantity()));
        return productWebMapper.toResponse(product);
    }
}
