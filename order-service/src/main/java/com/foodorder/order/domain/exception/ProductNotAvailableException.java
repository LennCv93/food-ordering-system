package com.foodorder.order.domain.exception;

public class ProductNotAvailableException extends RuntimeException {

    public ProductNotAvailableException(Long productId) {
        super("Product " + productId + " is not available");
    }
}
