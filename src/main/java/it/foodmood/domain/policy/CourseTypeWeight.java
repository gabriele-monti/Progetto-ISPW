package it.foodmood.domain.policy;

import it.foodmood.domain.value.CourseType;

public class CourseTypeWeight {
    
    public double weightFor(CourseType courseType){
        return switch(courseType){
            case APPETIZER -> 0.18;
            case FIRST_COURSE -> 0.40;
            case MAIN_COURSE -> 0.35;
            case DESSERT -> 0.20;
            case FRUIT -> 0.08;
            case BEVERAGE -> 0.05;
            case SIDE_DISH -> 0.10;
        };
    }
}
