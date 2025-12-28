package it.foodmood.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import it.foodmood.domain.value.Money;

public class TableSession {
    
    private final UUID id; // ID sessione
    private final int tableId; // Numero del tavolo
    private boolean open = true; // Stato della sessione

    private final List<Order> orders = new ArrayList<>(); // Lista di ordini effettuati nella sessione

    // Creo la sessione assegnando un UUID randomico, il numero del tavolo, stato Aperto
    public static TableSession create(int tableId){
        return new TableSession(UUID.randomUUID(), tableId, true, List.of());
    }

    public static TableSession fromPersistence(UUID id, int tableId, boolean open, List<Order> orders){
        return new TableSession(id, tableId, open, orders);
    }

    private TableSession(UUID id, int tableId, boolean open, List<Order> orders){
        this.id = Objects.requireNonNull(id, "L'ID della sessione non può essere nullo");
        if(tableId < 0) throw new IllegalArgumentException("Il numero del tavolo deve essere positivo");
        this.tableId = tableId;
        this.open = open;
        this.orders.addAll(orders);
    }

    public UUID getTableSessionId(){
        return id;
    }

    public int getTableId(){
        return tableId;
    }
    public Order newOrder(UUID userId){
        ensureOpen();

        Order order = Order.open(userId);
        orders.add(order);
        return order;
    }

    public List<Order> getOrders(){
        return Collections.unmodifiableList(orders);
    }

    public Money total(){
        Money total = Money.zero();
        for(Order order : orders){
            if(order.getStatus().isPayable()){
                total = total.add(order.total());
            }
        }
        return total;
    }

    public Money totalForUser(UUID userId){

        Money total = Money.zero();
        for(Order order : orders){
            if(order.getUserId().equals(userId) && order.getStatus().isPayable()){
                total = total.add(order.total());
            }
        }
        return total;
    }

    public void closePaid(){
        ensureOpen();

        boolean hasOpen = orders.stream().anyMatch(Order::isOpen);
        if(hasOpen) throw new IllegalStateException("Impossibile chiudere una sessione con ordini aperti");
        open = false;
    }

    public boolean isOpen(){
        return open;
    }

    private void ensureOpen(){
        if(!open) throw new IllegalStateException("Sessione tavolo chiusa");
    }
}
