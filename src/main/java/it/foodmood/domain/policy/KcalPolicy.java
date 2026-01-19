package it.foodmood.domain.policy;

import java.util.List;

public class KcalPolicy {

    public List<Integer> kcalOptions(int numberOfCourses){
        
        int light = 450 + 150 * (numberOfCourses - 1);
        int balanced = 800 + 250 * (numberOfCourses - 1);
        int complete = 1100 + 300 * (numberOfCourses - 1);
        
        return List.of(light, balanced, complete);
    }
}
