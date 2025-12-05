package it.foodmood.domain.model;

import java.util.Objects;
import java.util.Set;

import it.foodmood.domain.value.TablePosition;
import it.foodmood.domain.value.TableStatus;

public class Table {
    private int id;
    private final int seats;
    private TablePosition position;
    private TableStatus status;

    private static final Set<Integer> ALLOWED_SEATS = Set.of(2,4,6,8);

    public Table(int id, int seats, TablePosition position){
        this.id = id;
        if(!ALLOWED_SEATS.contains(seats)){
            throw new IllegalArgumentException("Tavolo con '" + seats + "' posti non valido per il dominio");
        }
        this.seats = seats;
        this.position = Objects.requireNonNull(position, "La posizione del tavolo non può essere nulla");
        this.status = TableStatus.FREE; // default
    }

    public Table(int id, int seats, TablePosition position, TableStatus status){
        this.id = id;
        if(!ALLOWED_SEATS.contains(seats)){
            throw new IllegalArgumentException("Tavolo con '" + seats + "' posti non valido per il dominio");
        }
        this.seats = seats;
        this.position = Objects.requireNonNull(position, "La posizione del tavolo non può essere nulla");
        this.status = Objects.requireNonNull(status, "Lo stato del tavolo non può essere nullo");
    }

    public int getId(){
        return id;
    }

    public int getSeats(){
        return seats;
    }
        
    public TablePosition getPosition(){
        return position;
    }

    public TableStatus getStatus(){
        return status;
    }

    public void moveTable(TablePosition newTablePosition){
        this.position = Objects.requireNonNull(newTablePosition, "La nuova posizione non può essere nulla");
    }

    public void changeStatus(TableStatus newStatus){
        this.status = Objects.requireNonNull(newStatus, "Lo stato del tavolo non può essere nullo");
    }
}
