package com.foodorder.user.application.dto;

public record LoginResult(String token, long expiresInSeconds) {
}
