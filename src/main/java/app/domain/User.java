package app.domain;

import java.time.Instant;

// Immutable User created with record instead of class
public record User(
        String id,
        String name,
        UserStatus status,
        Instant lastLogin
) {
}
