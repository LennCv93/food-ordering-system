package com.foodorder.order.application.port;

import com.foodorder.order.application.dto.ProductSnapshot;

public interface ProductCatalogPort {

    ProductSnapshot getProduct(Long productId, String authorizationHeader);
}
