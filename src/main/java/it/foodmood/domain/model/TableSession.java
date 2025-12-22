package it.foodmood.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import it.foodmood.domain.value.Money;

public class TableSession {
    
    private final UUID id = UUID.randomUUID();
    private final Table table;
    private boolean open = true;

    private final List<Order> orders = new ArrayList<>();
    private final List<SessionGuest> guests = new ArrayList<>();

    public TableSession(Table table, int initialCovers){
        this.table = Objects.requireNonNull(table, "Il tavolo non può essere nullo");
        if(initialCovers <= 0) throw new IllegalArgumentException("Il numero dei coperti deve essere maggiore di zero");
        if(initialCovers > table.getSeats()) throw new IllegalArgumentException("Tavolo pieno");
        
        for(int i = 1; i <= initialCovers; i++){
            guests.add(SessionGuest.guest(i));
        }
        this.table.occupy();
    }

    public UUID getTableSessionId(){
        return id;
    }

    public int getMaxSeats(){
        return table.getSeats();
    }

    public int getTableId(){
        return table.getId();
    }

    public Order newOrder(int guestNo){
        ensureGuest(guestNo);

        ensureOpen();
        Order order = Order.open(guestNo);
        orders.add(order);
        return order;
    }

    public List<Order> getOrders(){
        return Collections.unmodifiableList(orders);
    }

    public SessionGuest addGuest(){
        ensureOpen();

        if(guests.size() >= table.getSeats()){
            throw new IllegalStateException("Tavolo pieno");
        }

        int guestNo = guests.size() + 1;
        SessionGuest guest = SessionGuest.guest(guestNo);
        guests.add(guest);
        return guest;
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

    public Money totalForGuest(int guestNo){
        ensureGuest(guestNo);

        Money total = Money.zero();
        for(Order order : orders){
            if(order.getGuestNo() == guestNo && order.getStatus().isPayable()){
                total = total.add(order.total());
            }
        }
        return total;
    }

    public void closePaid(){
        ensureOpen();
        boolean hasOpen = orders.stream().anyMatch(Order::isOpen);
        if(hasOpen) throw new IllegalStateException("Hai ordini ancora aperti!");
        open = false;
        table.free();
    }

    public void linkCustomer(int guestNo, UUID customerId){
        ensureOpen();
        ensureGuest(guestNo);

        if(customerId == null){
            throw new IllegalArgumentException("ID cliente obbligatorio");
        }

        int index = guestNo - 1;
        SessionGuest current = guests.get(index);

        if(current.getCustomerId().isPresent()){
            UUID existing = current.getCustomerId().get();

            if(existing.equals(customerId)) return;

            throw new IllegalStateException("Questo guest è già associato ad un altro cliente");
        }

        boolean alreadyLinked = guests.stream().filter(g -> g.getGuestNo() != guestNo ).anyMatch(g -> g.getCustomerId().map(customerId::equals).orElse(false));

        if(alreadyLinked){
            throw new IllegalArgumentException("Cliente già associato a questa sessione.");
        }

        guests.set(index, SessionGuest.registered(guestNo, customerId));
    }

    public List<SessionGuest> getGuests(){
        return Collections.unmodifiableList(guests);
    }

    private void ensureGuest(int guestNo){
        if(guestNo <= 0 || guestNo > guests.size()){
            throw new IllegalArgumentException("Guest " + guestNo + " non presente in sessione");
        }
    }

    private void ensureOpen(){
        if(!open) throw new IllegalStateException("Sessione tavolo chiusa");
    }
}
