package com.foodorder.payment.infrastructure.web.controller;

import com.foodorder.payment.application.usecase.GetPaymentByOrderUseCase;
import com.foodorder.payment.application.usecase.GetPaymentUseCase;
import com.foodorder.payment.application.usecase.ProcessPaymentUseCase;
import com.foodorder.payment.infrastructure.web.dto.PayOrderRequest;
import com.foodorder.payment.infrastructure.web.dto.PaymentResponse;
import com.foodorder.payment.infrastructure.web.mapper.PaymentWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final GetPaymentUseCase getPaymentUseCase;
    private final GetPaymentByOrderUseCase getPaymentByOrderUseCase;
    private final ProcessPaymentUseCase processPaymentUseCase;
    private final PaymentWebMapper paymentWebMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public PaymentResponse getById(Authentication authentication, @PathVariable Long id) {
        return paymentWebMapper.toResponse(
                getPaymentUseCase.execute(id, Long.valueOf(authentication.getName()), isAdmin(authentication)));
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public PaymentResponse getByOrder(Authentication authentication, @PathVariable Long orderId) {
        return paymentWebMapper.toResponse(
                getPaymentByOrderUseCase.execute(orderId, Long.valueOf(authentication.getName()), isAdmin(authentication)));
    }

    @PostMapping("/order/{orderId}/pay")
    @PreAuthorize("hasRole('USER')")
    public PaymentResponse pay(Authentication authentication, @PathVariable Long orderId,
                                @Valid @RequestBody PayOrderRequest request) {
        Long requesterId = Long.valueOf(authentication.getName());
        return paymentWebMapper.toResponse(processPaymentUseCase.execute(orderId, request.paymentMethod(), requesterId));
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }
}
