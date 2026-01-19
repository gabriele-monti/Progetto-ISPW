package it.foodmood.domain.policy;

import java.util.Set;

import it.foodmood.domain.value.CourseType;

public class PriceWeight {
    
    public double weightFor(CourseType courseType){
        return switch(courseType){
            case APPETIZER -> 0.15;
            case FIRST_COURSE -> 0.30;
            case MAIN_COURSE -> 0.40;
            case PIZZA -> 0.27;
            case DESSERT -> 0.17;
            case FRUIT -> 0.10;
            case BEVERAGE -> 0.8;
            case SIDE_DISH -> 0.15;
        };
    }

    public double normalized(CourseType courseType, Set<CourseType> selected){
        if(selected == null || selected.isEmpty()) return 1.0;

        double sum = selected.stream().mapToDouble(this::weightFor).sum();

        if(sum <= 0) return 1.0;

        return weightFor(courseType)/sum;
    }
}
