package it.foodmood.persistence.dao;

import java.util.List;

import it.foodmood.domain.model.Dish;

public interface DishDAO extends CrudDAO<Dish, String>{
    List<Dish> findByCategory(String category);
    List<Dish> findByDietCategory(String dietCategory);
}
