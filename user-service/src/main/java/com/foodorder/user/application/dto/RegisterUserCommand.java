package com.foodorder.user.application.dto;

public record RegisterUserCommand(String firstName, String lastName, String email, String rawPassword) {
}
