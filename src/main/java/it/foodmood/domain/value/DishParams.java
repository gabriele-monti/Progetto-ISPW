package it.foodmood.domain.value;

import java.util.List;

public record DishParams(    
    String name,
    String description,
    CourseType courseType,
    DietCategory dietCategory,
    List<IngredientPortion> ingredients,
    DishState state,
    Image image,
    Money price) {}