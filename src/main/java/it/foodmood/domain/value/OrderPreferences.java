package it.foodmood.domain.value;

import java.util.Optional;
import java.util.Set;

public class OrderPreferences {

    private final Set<DietCategory> dietCategories;
    private final Set<CourseType> courseTypes;
    private final Set<Allergen> allergens;
    private final Optional<Integer> maxKcal;
    private final Optional<Money> maxBudget;

    public OrderPreferences (Set<DietCategory> dietCategories, Set<CourseType> courseTypes, Set<Allergen> allergens, Integer maxKcal, Money maxBudget){
        this.dietCategories = dietCategories == null ? Set.of() : Set.copyOf(dietCategories);
        this.courseTypes = courseTypes == null ? Set.of() : Set.copyOf(courseTypes);
        this.allergens = allergens == null ? Set.of() : Set.copyOf(allergens);
        this.maxKcal = Optional.ofNullable(maxKcal);
        this.maxBudget = Optional.ofNullable(maxBudget);
    }

    public Set<DietCategory> getDietCategories(){
        return dietCategories;
    }

    public Set<CourseType> getCourseType(){
        return courseTypes;
    }

    public Set<Allergen> getAllergens(){
        return allergens;
    }

    public Optional<Integer> getMaxKcal(){
        return maxKcal;
    }

    public Optional<Money> getMaxBudget(){
        return maxBudget;
    }
}
