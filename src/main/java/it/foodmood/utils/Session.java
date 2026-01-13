package it.foodmood.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Session {

    private final UUID userId; 
    private Instant expiryTime;

    private static final Duration DURATION = Duration.ofMinutes(50);

    public Session(UUID actorId){
        this.userId = Objects.requireNonNull(actorId);
        this.expiryTime = Instant.now().plus(DURATION);
    }

    public UUID getUserId(){
        return userId;
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
