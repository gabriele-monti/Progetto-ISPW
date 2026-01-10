package it.foodmood.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import it.foodmood.domain.value.Money;

public class Cart {
    private final UUID id;
    private final List<OrderLine> lines;

    public Cart(){
        this.id = UUID.randomUUID();
        this.lines = new ArrayList<>();
    }

    public void addLine(UUID dishId, String productName, Money unitPrice, int quantity){
        Objects.requireNonNull(dishId);
        Objects.requireNonNull(productName);
        Objects.requireNonNull(unitPrice);

        OrderLine line = new OrderLine(dishId, productName, unitPrice, quantity);
        lines.add(line);
    }

    public List<OrderLine> getLines(){
        return List.copyOf(lines);
    }

    public UUID getId(){
        return id;
    }

    public boolean isEmpty(){
        return lines.isEmpty();
    }

    public void clear(){
        lines.clear();
    }

    public BigDecimal getTotal(){
        Money total = Money.zero();
        for(OrderLine line : lines){
            total = total.add(line.getSubtotal());
        }
        return total.getAmount();
    }
}
