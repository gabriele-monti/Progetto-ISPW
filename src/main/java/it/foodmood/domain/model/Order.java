package it.foodmood.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import it.foodmood.domain.value.Money;
import it.foodmood.domain.value.OrderStatus;
import it.foodmood.exception.OrderException;

public class Order {
    
    private final UUID id;
    private final UUID userId;
    private OrderStatus status;
    private final List<OrderLine> orderLines;

    private Order(UUID id, UUID userId){
        this.id = Objects.requireNonNull(id, "L'ID dell'ordine non può essere nullo");
        this.userId = Objects.requireNonNull(userId, "L'ID del cliente non può essere nullo");
        this.status = OrderStatus.OPEN; 
        this.orderLines = new ArrayList<>();
    }

    public static Order open(UUID userId){
        return new Order(UUID.randomUUID(), userId);
    }

    public UUID getUserId(){
        return userId;
    }

    public UUID getId(){
        return id;
    }

    public OrderStatus getStatus(){
        return status;
    }

    public List<OrderLine> getOrderLines(){
        return List.copyOf(orderLines);
    }

    public void addProduct(UUID dishId, String name, Money unitPrice, int quantity){
        ensureModifiable();
        Objects.requireNonNull(dishId);
        Objects.requireNonNull(name);
        Objects.requireNonNull(unitPrice);

        if(quantity <= 0) {
            throw OrderException.invalidQuantity();
        }

        for(int i = 0; i < orderLines.size(); i++){
            OrderLine line = orderLines.get(i);
            if(line.sameItem(dishId, unitPrice)){
                orderLines.set(i, line.increaseQuantity(quantity));
                return;
            }
        }
        orderLines.add(new OrderLine(dishId, name, unitPrice, quantity));
    }

    public void removeProduct(UUID dishId){
        ensureModifiable();
        Objects.requireNonNull(dishId);
        orderLines.removeIf(line -> line.sameProduct(dishId));
    }

    public void removeLine(OrderLine line){
        ensureModifiable();
        Objects.requireNonNull(line);
        orderLines.remove(line);
    }

    public Money total(){
        Money total = Money.zero();
        for(OrderLine line : orderLines){
            total = total.add(line.subtotal());
        }
        return total;
    }

    public void confirm(){
        ensureModifiable();
        if(orderLines.isEmpty()){
            throw OrderException.emptyConfirm();
        }
        this.status = status.confirm();
    }

    public void cancel(){
        this.status = status.cancel();
    }

    public void served(){
        this.status = status.serve();
    }

    public boolean isOpen(){
        return status.isOpen();
    }

    private void ensureModifiable() {
        if(status != OrderStatus.OPEN){
            throw OrderException.notModificable(status);
        }
    }
}