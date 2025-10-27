package it.foodmood.persistence.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import it.foodmood.domain.model.Dish;
import it.foodmood.persistence.dao.DishDAO;

public class InMemoryDishDAO implements DishDAO{

    private final Map<String, Dish> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Dish dish){
        if(dish == null){
            throw new IllegalArgumentException("Dish non può essere nullo.");
        }
        if(dish.getId() == null){
            throw new IllegalArgumentException("L'ID del piatto non può essere nullo");
        }

        storage.put(dish.getId(), dish);
    }

    @Override
    public Optional<Dish> findById(String id){
        if(id == null || id.isBlank()){
            return Optional.empty();
        }
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Dish> findAll(){
        return List.copyOf(storage.values());
    }

    @Override 
    public void deleteById(String id){
        if(id == null || id.isBlank()){
            return;
        }
        storage.remove(id);
    }

    @Override
    public List<Dish> findByCategory(String category){
        if(category == null || category.isBlank()){
            return List.of();
        }

        String normalized = category.trim().toUpperCase();

        return storage.values().stream().filter(dish -> dish.getCourseType().name().equals(normalized)).collect(Collectors.toList());    
    }

    @Override
    public List<Dish> findByDietCategory(String dietCategory){
        if(dietCategory == null || dietCategory.isBlank()){
            return List.of();
        }

        String normalized = dietCategory.trim().toUpperCase();

        return storage.values().stream().filter(dish -> dish.getDietCategory().name().equals(normalized)).collect(Collectors.toList());    
    }
}
