package it.foodmood.domain.model;

import it.foodmood.domain.valueobject.Allergen;
import it.foodmood.domain.valueobject.Macronutrients;
import it.foodmood.domain.valueobject.Quantity;

import java.util.*;

public final class Ingredient {
    private final String name;
    private final Macronutrients macro;  // Riferiti a 100 g / ml
    private final Set<Allergen> allergens;

    private Ingredient(String name, Macronutrients macro, Set<Allergen> allergens){
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Il nome non può essere vuoto.");
        this.name = name;
        this.macro = macro;
        this.allergens = allergens == null ? Set.of() : Set.copyOf(allergens);
    }

    public static Ingredient of(String name, Macronutrients macro, Set<Allergen> allergens){
        return new Ingredient(name, macro, allergens);
    }

    public String name () {return name;}
    public Macronutrients macro() {return macro;}
    public Set<Allergen> allergens() {return allergens;}

    // GRASP: Information Expert -> kcal per la quantità usata
    public double kcalFor(Quantity qt){
        return macro.kcal() * qt.ratioToBase();
    }

    public Macronutrients macroFor(Quantity qt){
        Objects.requireNonNull(qt, "La quantità non può essere nulla!");
        return macro.scale(qt.ratioToBase());
    }

    public boolean isAllergenic() {return !allergens.isEmpty();}

    public boolean contains(Allergen allergen) {return allergens.contains(allergen);}

    @Override public String toString(){
        return "%s (per 100: %.1f kcal, allergeni = %s)".formatted(name, macro.kcal(), allergens);
    }
    
}
