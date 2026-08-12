package com.foodorder.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class User {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String passwordHash;
    private final Role role;
    private final Instant createdAt;
    private final Instant updatedAt;

    public static User createNew(String firstName, String lastName, String email, String passwordHash) {
        Instant now = Instant.now();
        return new User(null, firstName, lastName, email, passwordHash, Role.USER, now, now);
    }
}
