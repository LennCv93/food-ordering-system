package com.foodorder.order.infrastructure.client;

import com.foodorder.order.application.dto.ProductSnapshot;
import com.foodorder.order.application.exception.ProductCatalogUnavailableException;
import com.foodorder.order.application.port.ProductCatalogPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CatalogServiceClient implements ProductCatalogPort {

    private final RestClient catalogRestClient;

    @Override
    @CircuitBreaker(name = "catalogService", fallbackMethod = "fallback")
    @Retry(name = "catalogService")
    public ProductSnapshot getProduct(Long productId, String authorizationHeader) {
        return catalogRestClient.get()
                .uri("/api/v1/products/{id}", productId)
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .retrieve()
                .body(ProductSnapshot.class);
    }

    private ProductSnapshot fallback(Long productId, String authorizationHeader, Throwable ex) {
        throw new ProductCatalogUnavailableException(productId);
    }
}
