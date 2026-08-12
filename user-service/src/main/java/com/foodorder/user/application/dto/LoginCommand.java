package com.foodorder.user.application.dto;

public record LoginCommand(String email, String rawPassword) {
}
