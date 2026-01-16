package it.foodmood.domain.model;

import java.util.*;

import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.Macronutrients;
import it.foodmood.domain.value.Quantity;
import it.foodmood.domain.value.Unit;

public final class Ingredient {
    private final String name;
    private final Macronutrients macro;  // Riferiti a 100 g / ml
    private final Set<Allergen> allergens;
    private final Unit unit;

    public Ingredient(String name, Macronutrients macro, Unit unit, Set<Allergen> allergens){
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Il nome non può essere vuoto.");
        this.name = name;
        this.macro = macro;
        this.unit = unit;
        this.allergens = (allergens == null) ? Set.of() : Set.copyOf(allergens);
    }

    public String getName() {return name;}
    public Macronutrients getMacro() {return macro;}
    public Set<Allergen> getAllergens() {return allergens;}
    public Unit getUnit() {return unit;}

    // GRASP: Information Expert -> kcal per la quantità usata
    public double kcalFor(Quantity qt){
        return macro.kcal() * qt.ratioToBase();
    }

    public Macronutrients getMacroFor(Quantity qt){
        Objects.requireNonNull(qt, "La quantità non può essere nulla!");
        return macro.scale(qt.ratioToBase());
    }

    public boolean isAllergenic() {return !allergens.isEmpty();}

    public boolean contains(Allergen allergen) {return allergens.contains(allergen);}

    @Override public String toString(){
        return "%s (per 100: %.1f kcal, allergeni = %s)".formatted(name, macro.kcal(), allergens);
    }
    
}
