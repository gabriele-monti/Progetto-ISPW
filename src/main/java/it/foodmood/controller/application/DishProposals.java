package it.foodmood.controller.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.OrderWizardState;
import it.foodmood.domain.policy.KcalWeight;
import it.foodmood.domain.policy.PriceWeight;
import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.exception.OrderException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;

public class DishProposals {
    private final DishDao dishDao;
    private final PriceWeight priceWeight;
    private final KcalWeight kcalWeight;

    public DishProposals(){
        DaoFactory factory = DaoFactory.getInstance();
        this.dishDao = factory.getDishDao();
        this.kcalWeight = new KcalWeight();
        this.priceWeight = new PriceWeight();
    }

    public List<Dish> generate(OrderWizardState state) throws OrderException {
        Set<CourseType> selectedCourses = state.getCourseType();
        if(selectedCourses == null || selectedCourses.isEmpty()){
            throw new OrderException("Nessuna portata selezionata");
        }

        List<Dish> allFilteredDishes = new ArrayList<>();

        // Per ogni portata selezionata, recupera e filtra i piatti
        for(CourseType courseType : selectedCourses){
            List<Dish> dishesForCourse = dishDao.findByCourseType(courseType).stream()
                .filter(Dish::isAvailable)
                .filter(dish -> matchesDietCategory(dish, state))
                .filter(dish -> isSafeForAllergens(dish, state))
                .filter(dish -> isWithinKcalLimit(dish, courseType, state))
                .filter(dish -> isWithinBudgetLimit(dish, courseType, state))
                .toList();

            allFilteredDishes.addAll(dishesForCourse);
        }

        return allFilteredDishes;
    }

    private boolean matchesDietCategory(Dish dish, OrderWizardState state){
        Set<DietCategory> dietPreferences = state.getDietCategories();
        if(dietPreferences == null || dietPreferences.isEmpty()){
            return true;
        }

        Set<DietCategory> dishCategories = dish.getDietCategories();

        if(dietPreferences.contains(DietCategory.VEGETARIAN) && !(dishCategories.contains(DietCategory.VEGETARIAN) || dishCategories.contains(DietCategory.VEGAN))){
            return false;
        }

        if(dietPreferences.contains(DietCategory.VEGAN) && !dishCategories.contains(DietCategory.VEGAN)){
            return false;
        }

        for(DietCategory preferences : dietPreferences){
            if(preferences == DietCategory.VEGETARIAN || preferences == DietCategory.VEGAN) continue;
            if(!dishCategories.contains(preferences)) return false;
        }

        return true;
    }

    private boolean isSafeForAllergens(Dish dish, OrderWizardState state){
        Set<Allergen> userAllergens = state.getAllergens();
        if(userAllergens == null || userAllergens.isEmpty()){
            return true;
        }

        Set<Allergen> dishAllergens = dish.allergens();

        return Collections.disjoint(dishAllergens, userAllergens);
    }

    private boolean isWithinBudgetLimit(Dish dish, CourseType courseType, OrderWizardState state){
        Integer totalBudget = state.getBudgetPreference();
        if(totalBudget == null || totalBudget <= 0){
            return true;
        }

        Set<CourseType> selectedCourses = state.getCourseType();
        double normalizedWeight = priceWeight.normalized(courseType, selectedCourses);
        double maxPriceForThisCourse = totalBudget * normalizedWeight;

        return dish.getPrice().toDouble() <= maxPriceForThisCourse;
    }

    private boolean isWithinKcalLimit(Dish dish, CourseType courseType, OrderWizardState state){
        Integer totalKcal = state.getKcalPreference();
        if(totalKcal == null || totalKcal <= 0){
            return true;
        }

        Set<CourseType> selectedCourses = state.getCourseType();
        double normalizedWeight = kcalWeight.normalized(courseType, selectedCourses);
        double maxKcalForThisCourse = totalKcal * normalizedWeight;

        return dish.getKcal() <= maxKcalForThisCourse;
    }

}
