package com.foodorder.order.infrastructure.web.controller;

import com.foodorder.order.application.dto.CreateOrderCommand;
import com.foodorder.order.application.dto.OrderItemRequest;
import com.foodorder.order.application.usecase.CancelOrderUseCase;
import com.foodorder.order.application.usecase.CreateOrderUseCase;
import com.foodorder.order.application.usecase.GetOrderUseCase;
import com.foodorder.order.application.usecase.ListOrdersUseCase;
import com.foodorder.order.domain.model.Order;
import com.foodorder.order.infrastructure.web.dto.CreateOrderRequest;
import com.foodorder.order.infrastructure.web.dto.OrderResponse;
import com.foodorder.order.infrastructure.web.dto.PageResponse;
import com.foodorder.order.infrastructure.web.mapper.OrderWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final ListOrdersUseCase listOrdersUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final OrderWebMapper orderWebMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponse> create(Authentication authentication,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                 @Valid @RequestBody CreateOrderRequest request) {
        Long userId = Long.valueOf(authentication.getName());
        var items = request.items().stream()
                .map(item -> new OrderItemRequest(item.productId(), item.quantity()))
                .toList();
        Order order = createOrderUseCase.execute(
                new CreateOrderCommand(userId, request.deliveryAddress(), items, authorizationHeader));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderWebMapper.toResponse(order));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public OrderResponse getById(Authentication authentication, @PathVariable Long id) {
        Order order = getOrderUseCase.execute(id, Long.valueOf(authentication.getName()), isAdmin(authentication));
        return orderWebMapper.toResponse(order);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public PageResponse<OrderResponse> list(Authentication authentication,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        return orderWebMapper.toPageResponse(
                listOrdersUseCase.execute(Long.valueOf(authentication.getName()), isAdmin(authentication), page, size));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public OrderResponse cancel(Authentication authentication, @PathVariable Long id) {
        Order order = cancelOrderUseCase.execute(id, Long.valueOf(authentication.getName()), isAdmin(authentication));
        return orderWebMapper.toResponse(order);
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }
}
