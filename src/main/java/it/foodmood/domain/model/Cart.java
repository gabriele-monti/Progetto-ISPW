package it.foodmood.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import it.foodmood.domain.value.Money;

public class Cart {
    private final List<CartItem> items;

    public Cart(){
        this.items = new ArrayList<>();
    }

    public void addLine(UUID dishId, String productName, Money unitPrice, int quantity){
        Objects.requireNonNull(dishId);
        Objects.requireNonNull(productName);
        Objects.requireNonNull(unitPrice);

        if(quantity <= 0){
            throw new IllegalArgumentException("La quantità deve essere maggiore di zero");
        }

        for(int i = 0; i < items.size(); i++){
            CartItem existing = items.get(i);
            if(existing.getDishId().equals(dishId)){
                int newQuantity = existing.getQuantity() + quantity;
                items.set(i, new CartItem(dishId, productName, unitPrice, newQuantity));
                return;
            }
        }
        
        items.add(new CartItem(dishId, productName, unitPrice, quantity));
    }

    public void removeLine(UUID dishId){
        items.removeIf(line -> line.getDishId().equals(dishId));
    }

    public List<CartItem> getItems(){
        return List.copyOf(items);
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public void clear(){
        items.clear();
    }

    public BigDecimal getTotal(){
        Money total = Money.zero();
        for(CartItem item : items){
            total = total.add(item.getSubtotal());
        }
        return total.getAmount();
    }
}
