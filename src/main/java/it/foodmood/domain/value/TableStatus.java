package it.foodmood.domain.value;

public enum TableStatus {
    FREE("LIBERO"),
    BOOKED("PRENOTATO"),
    OCCUPIED("OCCUPATO");

    private final String description;

    TableStatus(String description){
        this.description = description;
    }

    public String description(){
        return description;
    }

    @Override
    public String toString(){
        return description;
    }
}
