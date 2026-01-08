package it.foodmood.domain.value;

import java.util.HashSet;
import java.util.Set;

public class OrderPreferences {

    private Set<DietCategory> dietCategories;
    private Set<CourseType> courseTypes;
    private Set<Allergen> allergens;
    private Integer maxKcal;
    private Money maxBudget;

    public OrderPreferences (Set<DietCategory> dietCategories, Set<CourseType> courseTypes, Set<Allergen> allergens, Integer maxKcal, Money maxBudget){
        this.dietCategories = dietCategories != null ? new HashSet<>(dietCategories) : new HashSet<>();
        this.courseTypes = courseTypes != null ? new HashSet<>(courseTypes) : new HashSet<>();
        this.allergens = allergens != null ? new HashSet<>(allergens) : new HashSet<>();
        this.maxKcal = maxKcal;
        this.maxBudget = maxBudget;
    }

    public Set<DietCategory> getDietCategories(){
        return dietCategories;
    }

    public Set<CourseType> getCourseTypes(){
        return courseTypes;
    }

    public Set<Allergen> getAllergens(){
        return allergens;
    }

    public Integer getMaxKcal(){
        return maxKcal;
    }

    public Money getMaxBudget(){
        return maxBudget;
    }
}
