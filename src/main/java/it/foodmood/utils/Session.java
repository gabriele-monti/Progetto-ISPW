package it.foodmood.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import it.foodmood.domain.value.Role;

public class Session {

    private final UUID userId; 
    private final String token;
    private final Role role;
    private Instant expiryTime;

    private static final Duration DURATION = Duration.ofMinutes(50);

    public Session(UUID userId, Role role){
        this.userId = Objects.requireNonNull(userId);
        this.role = Objects.requireNonNull(role);
        this.token = UUID.randomUUID().toString();
        this.expiryTime = Instant.now().plus(DURATION);
    }

    public UUID getUserId(){
        return userId;
    }

    public String getToken(){
        return token;
    }

    public Role getRole(){
        return role;
    }

    public Instant getExpiryTime(){
        return expiryTime;
    }

    public boolean isExpired(){
        return Instant.now().isAfter(expiryTime);
    }

    public void refresh(){
        this.expiryTime = Instant.now().plus(DURATION);
    }

}
