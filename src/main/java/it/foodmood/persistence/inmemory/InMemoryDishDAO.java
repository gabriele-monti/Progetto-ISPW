package it.foodmood.persistence.inmemory;

import java.util.List;

import it.foodmood.domain.model.Dish;
import it.foodmood.persistence.dao.DishDAO;

public class InMemoryDishDAO extends AbstractInMemoryCrudDAO<Dish, String> implements DishDAO{

    protected String getId(Dish dish){
        return dish.getId();
    }

    @Override
    public List<Dish> findByCategory(String category){
        if(category == null || category.isBlank()){
            return List.of();
        }

        String normalized = category.trim().toUpperCase();

        return storage.values().stream().filter(dish -> dish.getCourseType().name().equals(normalized)).toList();    
    }

    @Override
    public List<Dish> findByDietCategory(String dietCategory){
        if(dietCategory == null || dietCategory.isBlank()){
            return List.of();
        }

        String normalized = dietCategory.trim().toUpperCase();

        return storage.values().stream().filter(dish -> dish.getDietCategory().name().equals(normalized)).toList();    
    }
}
