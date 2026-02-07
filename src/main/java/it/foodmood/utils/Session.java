package it.foodmood.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import it.foodmood.domain.model.Cart;
import it.foodmood.domain.model.OrderFlowState;
import it.foodmood.domain.value.OrderComplexity;

public class Session {

    private final UUID userId; 
    private Instant expiryTime;
    private Cart cart;
    private OrderFlowState orderFlowState;
    private OrderComplexity orderComplexity;

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

    public Cart getCart(){
        if(cart == null){
            cart = new Cart();
        }
        return cart;
    }

    public void clearCart(){
        if(cart != null){
            cart.clear();
        }
    }

    public OrderFlowState getOrderFlowState(){
        return orderFlowState;
    }

    public void setOrderFlowState(OrderFlowState orderFlowState){
        this.orderFlowState = orderFlowState;
    }

    public OrderComplexity getOrderComplexity(){
        return orderComplexity;
    }

    public void setOrderComplexity(OrderComplexity orderComplexity){
        this.orderComplexity = orderComplexity;
    }
}
