package it.foodmood.domain.model;

import java.util.UUID;

public class Credential {
    private final UUID userId;
    private String passwordHash;

    public Credential(UUID userId, String passwordHash){
        this.userId = userId;
        this.passwordHash = passwordHash;
    }

    public UUID getUserId(){
        return userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
