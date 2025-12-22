package it.foodmood.bean;

import java.util.Set;

import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.Budget;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.Kcal;

public class OrderPreferencesBean {
    
    private Set<DietCategory> dietCategories;
    private Set<CourseType> courseTypes;
    private Set<Allergen> allergens;
    private Budget budget;
    private Kcal kcal;

    public OrderPreferencesBean(){
        this.dietCategories = Set.of();
        this.courseTypes = Set.of();
        this.allergens = Set.of();
    }

    public Set<DietCategory> getDietCategories(){
        return dietCategories;
    }

    public void setDietCategories(Set<DietCategory> dietCategories){
        this.dietCategories = (dietCategories == null) ? Set.of() : Set.copyOf(dietCategories);    
    }

    public Set<CourseType> getCourseTypes(){
        return courseTypes;
    }

    public void setCourseTypes(Set<CourseType> courseTypes){
        this.courseTypes = (courseTypes == null) ? Set.of() : Set.copyOf(courseTypes);    
    }

    public Set<Allergen> getAllergens(){
        return allergens;
    }

    public void setAllergens(Set<Allergen> allergens){
        this.allergens = (allergens == null) ? Set.of() : Set.copyOf(allergens);    
    }

    public Budget getBudget(){
        return budget;
    }

    public void setBudget(Budget budget){
        this.budget = budget;
    }

    public Kcal getKcal(){
        return kcal;
    }

    public void setKcal(Kcal kcal){
        this.kcal = kcal;
    }
}
