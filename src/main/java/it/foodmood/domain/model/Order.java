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
    private final int guestNo;
    private OrderStatus status;
    private final List<OrderLine> orderLines;

    private Order(UUID id, int guestNo){
        this.id = Objects.requireNonNull(id);
        if(guestNo <= 0) throw new IllegalArgumentException("Guest number non valido");
        this.guestNo = guestNo;
        this.status = OrderStatus.OPEN; 
        this.orderLines = new ArrayList<>();
    }

    public static Order open(int guestNo){
        return new Order(UUID.randomUUID(), guestNo);
    }

    public int getGuestNo(){
        return guestNo;
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