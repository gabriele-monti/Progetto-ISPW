package it.foodmood.domain.value;

public enum DishState {
    AVAILABLE("Disponibile"),
    UNAVAILABLE("Non disponibile");

    private final String description;

    DishState(String description){
        this.description = description;
    }

    public String description(){
        return description;
    }

    @Override
    public String toString(){
        return name() + " - " + description;
    }
}
