package it.foodmood.domain.policy;

import it.foodmood.domain.value.CourseType;

public class PriceWeight {
    
    public double weightFor(CourseType courseType){
        return switch(courseType){
            case APPETIZER -> 0.15;
            case FIRST_COURSE -> 0.30;
            case MAIN_COURSE -> 0.40;
            case DESSERT -> 0.15;
            case FRUIT -> 0.06;
            case BEVERAGE -> 0.08;
            case SIDE_DISH -> 0.10;
        };
    }
}
