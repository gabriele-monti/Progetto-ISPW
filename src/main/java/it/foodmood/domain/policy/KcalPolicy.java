package it.foodmood.domain.policy;

import java.util.List;

public class KcalPolicy {

    public List<Integer> kcalOptions(int numberOfCourses){
        
        int light = 350 + 150 * (numberOfCourses - 1);
        int balanced = 700 + 250 * (numberOfCourses - 1);
        int complete = 1000 + 300 * (numberOfCourses - 1);
        
        return List.of(light, balanced, complete);
    }
}
