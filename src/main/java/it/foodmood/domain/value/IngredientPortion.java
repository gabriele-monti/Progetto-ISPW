package it.foodmood.domain.value;

import java.util.Objects;

import it.foodmood.domain.model.Ingredient;

/*
 * Rappresenta la porzione di un ingrediente
 */

public final class IngredientPortion{

    private final Ingredient ingredient;
    private final Quantity quantity;

    public IngredientPortion(Ingredient ingredient, Quantity quantity){
        this.ingredient = Objects.requireNonNull(ingredient, "Ingrediente non può essere nullo.");
        this.quantity = Objects.requireNonNull(quantity, "Quantità non può essere nulla.");
    }

    public Ingredient getIngredient() {return ingredient;}

    public Quantity getQuantity() {return quantity;}

    public IngredientPortion withQuantity(Quantity newQuantity){
        return new IngredientPortion(this.ingredient, Objects.requireNonNull(newQuantity, "La nuova quantità non può essere nulla"));
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof IngredientPortion other)) return false;
        return ingredient.equals(other.ingredient) && quantity.equals(other.quantity);
    }

    @Override
    public int hashCode(){
        return Objects.hash(ingredient, quantity);
    }

    @Override
    public String toString(){
        return quantity + " x " + ingredient.getName();
    }
}