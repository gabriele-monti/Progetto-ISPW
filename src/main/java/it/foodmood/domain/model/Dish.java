package it.foodmood.domain.model;

import it.foodmood.domain.valueobject.Allergen;
import it.foodmood.domain.valueobject.CourseType;
import it.foodmood.domain.valueobject.DietCategory;
import it.foodmood.domain.valueobject.IngredientPortion;
import it.foodmood.domain.valueobject.Macronutrients;
import it.foodmood.domain.valueobject.Money;
import it.foodmood.domain.valueobject.Image;

import java.util.*;
import java.util.stream.Collectors;

public final class Dish {
    private final String name;
    private final String description;
    private final CourseType courseType;
    private final DietCategory dietCategory;
    private final List<IngredientPortion> ingredients;
    private final Image image;
    private final Money price;
    
    private Dish (Builder builder){
        if(builder.name == null || builder.name.isBlank()){
            throw new IllegalArgumentException("Il nome non può essere vuoto!");
        }
        if(builder.ingredients == null || builder.ingredients.isEmpty()){
            throw new IllegalArgumentException("Deve esserci almeno un ingrediente!");
        }

        this.name = builder.name;
        this.description = builder.description;
        this.courseType = Objects.requireNonNull(builder.courseType, "Tipologia del piatto obbligatoria");
        this.dietCategory = Objects.requireNonNull(builder.dietCategory, "Categoria del piatto obbligatoria");
        this.ingredients = List.copyOf(builder.ingredients);
        this.image = builder.image;
        this.price = Objects.requireNonNull(builder.price, "Prezzo obbligatorio");
    }

    public String name() {return name; }

    public Optional<String> description() {return Optional.ofNullable(description); }

    public CourseType courseType() {return courseType; }

    public DietCategory dietCategory() {return dietCategory; }

    public List<IngredientPortion> ingredients() {return ingredients; }

    public Optional<Image> image() {return Optional.ofNullable(image); }

    public Money price() {return price; }

    public Macronutrients totalMacronutrients(){
        return ingredients.stream().map(portion -> portion.ingredient().macroFor(portion.quantity())).reduce(Macronutrients.zero(), Macronutrients::plus);
    }

    public double totalKcal(){
        return totalMacronutrients().kcal();
    }

    public Set<Allergen> allergens(){
        return ingredients.stream().flatMap(portion -> portion.ingredient().allergens().stream()).collect(Collectors.toUnmodifiableSet());
    }

    public boolean isAllergenic(){
        return !allergens().isEmpty();
    }

    public boolean containsAllergen(Allergen allergen){
        Objects.requireNonNull(allergen, "L'allergene non può essere nullo");
        return allergens().contains(allergen);
    }

    public static class Builder{
        private String name;
        private CourseType courseType;
        private DietCategory dietCategory;
        private Money price;
        private List<IngredientPortion> ingredients = new ArrayList<>();
        private String description;
        private Image image;
        
        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder courseType(CourseType courseType){
            this.courseType = courseType;
            return this;
        }

        public Builder dietCategory(DietCategory dietCategory){
            this.dietCategory = dietCategory;
            return this;
        }

        public Builder price(Money price){
            this.price = price;
            return this;
        }

        public Builder image(Image image){
            this.image = image;
            return this;
        }

        public Builder addIngredient(IngredientPortion portion){
            Objects.requireNonNull(portion, "L'ingrediente non può essere nullo");
            this.ingredients.add(portion);
            return this;
        }

        public Builder ingredients(List<IngredientPortion> ingredients){
            Objects.requireNonNull(ingredients, "La lista degli ingredienti non può essere nulla");
            this.ingredients = new ArrayList<>(ingredients);
            return this;
        }

        public Dish build(){
            return new Dish(this);
        }
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(!(o instanceof Dish dish)) return false;
        return name.equals(dish.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }

    @Override
    public String toString(){
        return "%s (%s - %s) - %.0f kcal, %d ingredienti, allergeni: %s, prezzo: %s".formatted(
            name,
            courseType.description(),
            dietCategory.description(),
            totalKcal(),
            ingredients.size(),
            allergens(),
            price
        );
    }
}
