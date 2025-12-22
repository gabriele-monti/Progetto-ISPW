package it.foodmood.domain.policy;

import java.util.Optional;

import it.foodmood.domain.value.Budget;
import it.foodmood.domain.value.Money;
import it.foodmood.domain.value.OrderPreferences;

public class PricePolicy {
    public Optional<Money> calculateMaxBudget(Budget budget, OrderPreferences preferences){
        int numberOfCourses = preferences.getCourseType().size();

        return switch(budget){
            case ECONOMIC -> Optional.of(Money.euro(15 + 10 * (numberOfCourses - 1)));
            case BALANCED -> Optional.of(Money.euro(30 + 15 * (numberOfCourses - 1)));
            case PREMIUM -> Optional.of(Money.euro(50 + 20 * (numberOfCourses - 1)));
            case FREE -> Optional.empty();
        };
    }
}
