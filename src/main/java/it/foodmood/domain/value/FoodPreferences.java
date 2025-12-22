package it.foodmood.domain.value;

import java.util.Set;

public final class FoodPreferences {

    public static final FoodPreferences EMPTY = new FoodPreferences(Set.of(), Set.of());
    
    private final Set<DietCategory> dietCategories;
    private final Set<Allergen> allergens;

    public FoodPreferences(Set<DietCategory> dietCategories, Set<Allergen> allergens){
        this.dietCategories = dietCategories == null ? Set.of() : Set.copyOf(dietCategories);
        this.allergens = allergens == null ? Set.of() : Set.copyOf(allergens);
    }

    public Set<DietCategory> getDietCategories(){
        return dietCategories;
    }

    public Set<Allergen> getAllergens(){
        return allergens;
    }
}
