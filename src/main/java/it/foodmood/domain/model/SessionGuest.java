package it.foodmood.domain.model;

import java.util.Optional;
import java.util.UUID;

public final class SessionGuest {
    private final int guestNo;
    private final UUID customerId; // null se utente guest

    private SessionGuest(int guestNo, UUID customerId){
        if(guestNo <= 0) throw new IllegalArgumentException("Guest number non valido");
        this.guestNo = guestNo;
        this.customerId = customerId;
    }

    public static SessionGuest guest(int guestNo){
        return new SessionGuest(guestNo, null);
    }

    public static SessionGuest registered(int guestNo, UUID customerId){
        if(customerId == null) {
            throw new IllegalArgumentException("ID cliente obbligatorio");
        }
        return new SessionGuest(guestNo, customerId);
    }

    public int getGuestNo(){
        return guestNo;
    }

    public Optional<UUID> getCustomerId(){
        return Optional.ofNullable(customerId);
    }

    public boolean isRegistered(){
        return customerId != null;
    }
}
