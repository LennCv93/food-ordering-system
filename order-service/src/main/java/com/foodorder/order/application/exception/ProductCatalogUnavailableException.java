package com.foodorder.order.application.exception;

public class ProductCatalogUnavailableException extends RuntimeException {

    public ProductCatalogUnavailableException(Long productId) {
        super("Catalog service unavailable while fetching product " + productId);
    }
}
