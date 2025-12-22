package it.foodmood.domain.policy;

import java.util.Optional;

import it.foodmood.domain.value.Kcal;
import it.foodmood.domain.value.OrderPreferences;

public class KcalPolicy {
    public Optional<Integer> calculateMaxKcal(Kcal kcal, OrderPreferences preferences){
        
        int numberOfCourses = preferences.getCourseType().size();

        return switch(kcal){
            case LIGHT -> Optional.of(350 + 150 * (numberOfCourses - 1));
            case BALANCED -> Optional.of(700 + 250 * (numberOfCourses - 1));
            case COMPLETE -> Optional.of(1000 + 300 * (numberOfCourses - 1));
            case FREE -> Optional.empty();
        };
    }
}
