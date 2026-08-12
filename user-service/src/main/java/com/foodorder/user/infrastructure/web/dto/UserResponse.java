package com.foodorder.user.infrastructure.web.dto;

import com.foodorder.user.domain.model.Role;

public record UserResponse(Long id, String firstName, String lastName, String email, Role role) {
}
