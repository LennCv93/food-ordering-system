package com.foodorder.user.infrastructure.web.controller;

import com.foodorder.user.application.usecase.GetUserByIdUseCase;
import com.foodorder.user.application.usecase.GetUserProfileUseCase;
import com.foodorder.user.application.usecase.ListUsersUseCase;
import com.foodorder.user.infrastructure.web.dto.PageResponse;
import com.foodorder.user.infrastructure.web.dto.UserResponse;
import com.foodorder.user.infrastructure.web.mapper.UserWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final GetUserProfileUseCase getUserProfileUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UserWebMapper userWebMapper;

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserResponse getProfile(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        return userWebMapper.toResponse(getUserProfileUseCase.execute(userId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<UserResponse> listUsers(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size) {
        return userWebMapper.toPageResponse(listUsersUseCase.execute(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getById(@PathVariable Long id) {
        return userWebMapper.toResponse(getUserByIdUseCase.execute(id));
    }
}
