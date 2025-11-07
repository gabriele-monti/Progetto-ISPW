package it.foodmood.domain.model;

import java.util.*;
import java.util.stream.Collectors;

import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.Image;
import it.foodmood.domain.value.IngredientPortion;
import it.foodmood.domain.value.Macronutrients;
import it.foodmood.domain.value.Money;

public final class Dish {
    private final String id;
    private final String name;
    private final String description;
    private final CourseType courseType;
    private final DietCategory dietCategory;
    private final List<IngredientPortion> ingredients;
    private final Image image;
    private final Money price;
    
    private Dish (Builder builder){

        this.id =(builder.id != null && !builder.id.isBlank()) ? builder.id : UUID.randomUUID().toString(); 

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

    public String getId() {return id;}

    public String getName() {return name; }

    public Optional<String> getDescription() {return Optional.ofNullable(description); }

    public CourseType getCourseType() {return courseType; }

    public DietCategory getDietCategory() {return dietCategory; }

    public List<IngredientPortion> getIngredients() {return ingredients; }

    public Optional<Image> getImage() {return Optional.ofNullable(image); }

    public Money getPrice() {return price; }

    public Macronutrients totalMacronutrients(){
        return ingredients.stream().map(portion -> portion.getIngredient().getmacroFor(portion.getQuantity())).reduce(Macronutrients.zero(), Macronutrients::plus);
    }

    public double totalKcal(){
        return totalMacronutrients().kcal();
    }

    public Set<Allergen> allergens(){
        return ingredients.stream().flatMap(portion -> portion.getIngredient().getAllergens().stream()).collect(Collectors.toUnmodifiableSet());
    }

    public boolean isAllergenic(){
        return !allergens().isEmpty();
    }

    public boolean containsAllergen(Allergen allergen){
        Objects.requireNonNull(allergen, "L'allergene non può essere nullo");
        return allergens().contains(allergen);
    }

    public static class Builder{
        private String id;
        private String name;
        private CourseType courseType;
        private DietCategory dietCategory;
        private Money price;
        private List<IngredientPortion> ingredients = new ArrayList<>();
        private String description;
        private Image image;

        public Builder setId(String id){
            this.id = id;
            return this;
        }
        
        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setDescription(String description){
            this.description = description;
            return this;
        }

        public Builder setCourseType(CourseType courseType){
            this.courseType = courseType;
            return this;
        }

        public Builder setDietCategory(DietCategory dietCategory){
            this.dietCategory = dietCategory;
            return this;
        }

        public Builder setPrice(Money price){
            this.price = Objects.requireNonNull(price, "Il prezzo non può essere nullo");
            return this;
        }

        public Builder setImage(Image image){
            this.image = image;
            return this;
        }

        public Builder addIngredient(IngredientPortion portion){
            Objects.requireNonNull(portion, "L'ingrediente non può essere nullo");
            this.ingredients.add(portion);
            return this;
        }

        public Builder setIngredients(List<IngredientPortion> ingredients){
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
        return id.equals(dish.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
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
