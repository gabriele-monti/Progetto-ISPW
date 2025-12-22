package it.foodmood.domain.model;

import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.DishState;
import it.foodmood.domain.value.Image;
import it.foodmood.domain.value.IngredientPortion;
import it.foodmood.domain.value.Macronutrients;
import it.foodmood.domain.value.Money;

import java.util.*;

public class Dish {
    private final UUID id;
    private String name;
    private String description;
    private CourseType courseType;
    private DietCategory dietCategory;
    private List<IngredientPortion> ingredients;
    private DishState state;
    private Image image;
    private Money price;

    private Dish(UUID id, String name, String description, CourseType courseType, DietCategory dietCategory, List<IngredientPortion> ingredients, DishState state, Image image, Money price) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name, "Il nome non può essere nullo").trim();
        if(this.name.isEmpty()) throw new IllegalArgumentException("Il nome non può essere vuoto");
        this.description = description; // opzionale
        this.courseType = Objects.requireNonNull(courseType, "Il tipo della portata non può essere nullo");
        this.dietCategory = Objects.requireNonNull(dietCategory,"La categoria dietetica non può essere nulla.");
        this.ingredients = new ArrayList<>();
        if(ingredients != null){
            for(IngredientPortion portion : ingredients){
                this.ingredients.add(Objects.requireNonNull(portion, "La porzione dell'ingrediente non può essere nulla"));
            }
        }
        this.state = Objects.requireNonNull(state, "Lo stato non può essere nullo");
        this.image = image; // opzionale
        this.price = Objects.requireNonNull(price, "Il prezzo non può essere nullo");
        if(!this.price.isPositive()) throw new IllegalArgumentException("Il prezzo deve essere maggiore di 0");
    }

    public static Dish create(String name, String description, CourseType courseType, DietCategory dietCategory, List<IngredientPortion> ingredients, DishState state, Image image, Money price) {
        return new Dish(UUID.randomUUID(), name, description, courseType, dietCategory, ingredients, state, image, price);
    }

    public static Dish fromPersistence(UUID id, String name, String description, CourseType courseType, DietCategory dietCategory, List<IngredientPortion> ingredients, DishState state, Image image, Money price) {
        return new Dish(id, name, description, courseType, dietCategory, ingredients, state, image, price);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public DietCategory getDietCategory() {
        return dietCategory;
    }

    public List<IngredientPortion> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public DishState getState() {
        return state;
    }

    public Image getImage() {
        return image;
    }

    public Money getPrice() {
        return price;
    }

    public void setIngredients(List<IngredientPortion> ingredients) {
        List<IngredientPortion> copy = new ArrayList<>();
        if(ingredients != null){
            for(IngredientPortion portion : ingredients){
                copy.add(Objects.requireNonNull(portion, "L'ingrediente non può essere nullo"));
            }
        }
        this.ingredients = copy;
    }

    // Metodi
    public void addIngredient(IngredientPortion portion){
        Objects.requireNonNull(portion, "L'ingrediente non può essere nullo");
        this.ingredients.add(portion);
    }

    public void removeIngredient(IngredientPortion portion){
        if(portion == null) return;
        this.ingredients.remove(portion);
    }

    public Macronutrients totalMacronutrients(){
        Macronutrients total = new Macronutrients(0, 0, 0);

        for(IngredientPortion portion : ingredients){
            Macronutrients macronutrients = portion.getIngredient().getMacroFor(portion.getQuantity());
            total = total.plus(macronutrients);
        }
        return total;
    }

    public double totalKcal(){
        return totalMacronutrients().kcal();
    }

    public void changeName(String newName){
        this.name = Objects.requireNonNull(newName, "Il nuovo nome non può essere nullo").trim();
        if(this.name.isEmpty()) throw new IllegalArgumentException("Il nome non può essere vuoto");
    }

    public void changeState(DishState newState){
        this.state = Objects.requireNonNull(newState, "Il nuovo stato non può essere nullo");
    }

    public void changeImage(Image newImage){
        this.image = newImage;
    }

    public void changePrice(Money newPrice){
        this.price = Objects.requireNonNull(newPrice, "Il nuovo prezzo non può essere nullo");
        if(!this.price.isPositive()) throw new IllegalArgumentException("Il prezzo deve essere maggiore di 0");
    }

    public void changeDescription(String newDescription){
        this.description = newDescription;
    }

    public void changeDietCategory(DietCategory newCategory){
        this.dietCategory = Objects.requireNonNull(newCategory, "La nuova categoria dietetica non può essere nulla");
    }

    public void changeCourseType(CourseType newCourseType){
        this.courseType = Objects.requireNonNull(newCourseType, "La nuova tipologia della portata non può essere nulla");
    }

    public Set<Allergen> allergens(){

        Set<Allergen> result = new HashSet<>();

        for(IngredientPortion portion : ingredients){
            result.addAll(portion.getIngredient().getAllergens());
        }

        return Collections.unmodifiableSet(result);
    }

    public boolean isAllergenic(){
        return !allergens().isEmpty();
    }

    public boolean containsAllergen(Allergen allergen){
        Objects.requireNonNull(allergen, "L'allergene non può essere nullo");
        return allergens().contains(allergen);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Dish other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
