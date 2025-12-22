package it.foodmood.domain.model;

import java.util.Objects;
import java.util.UUID;

import it.foodmood.domain.value.Money;
import it.foodmood.exception.OrderException;

public record OrderLine(UUID dishId, String productName, Money unitPrice, int quantity) {

    public OrderLine {
        Objects.requireNonNull(dishId, "L'ID del prodotto non può essere nullo");
        Objects.requireNonNull(productName, "Il nome del prodotto non può essere nullo");
        Objects.requireNonNull(unitPrice, "Il prezzo non può essere nullo");

        if(productName.isBlank()) throw new IllegalArgumentException("Nome prodotto vuoto");
        if(!unitPrice.isPositive()) throw OrderException.invalidPrice();
        if(quantity <= 0) throw OrderException.invalidQuantity();
    }

    public Money subtotal(){
        return unitPrice.multiply(quantity);
    }

    public OrderLine increaseQuantity(int item){
        if(item <= 0) throw OrderException.invalidQuantity();
        return new OrderLine(dishId, productName, unitPrice, quantity + item);
    }

    public OrderLine changeQuantity(int newQuantity){
        if(newQuantity <= 0) throw OrderException.invalidQuantity();
        return new OrderLine(dishId, productName, unitPrice, newQuantity);
    }


    public boolean sameProduct(UUID newDishId){
        return dishId.equals(newDishId);
    }

    public boolean sameItem(UUID newDishId, Money newPrice){
        return dishId.equals(newDishId) && unitPrice.equals(newPrice);
    }
}
