package it.foodmood.domain.valueobject;

/*
 * Categoria dietetica del piatto o del prodotto commerciale
*/

public enum DietCategory {
    TRADITIONAL("Tradizionale"),
    VEGETARIAN("Vegetariana"),
    VEGAN("Vegana"),
    GLUTEN_FREE("Senza glutine"),
    LACTOSE_FREE("Senza lattosio");

    private final String description;

    DietCategory(String description){
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
