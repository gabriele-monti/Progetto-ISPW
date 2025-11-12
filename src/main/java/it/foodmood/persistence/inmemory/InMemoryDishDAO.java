package it.foodmood.persistence.inmemory;

import java.util.List;
import java.util.Optional;

import it.foodmood.domain.model.Dish;
import it.foodmood.persistence.dao.DishDao;

public class InMemoryDishDao implements DishDao {

    private static InMemoryDishDao instance = null;

    public static synchronized InMemoryDishDao getInstance(){
        if(instance == null){
            instance = new InMemoryDishDao();
        }
        return instance;
    }

    @Override
    public void insert(Dish entity){
        //Timplementare
        throw new UnsupportedOperationException("Non ancora imlementato");
    }

    @Override
    public List<Dish> findAll(){
        //Timplementare
        return List.of();
    }

    @Override
    public Optional<Dish> findById(String id){
        //implementare
        return Optional.empty();
    }

    @Override
    public void deleteById(String id){
        //implementare
    }

    @Override
    public List<Dish> findByCategory(String entity){
        //implementare
        return List.of();
    }
    @Override
    public List<Dish> findByDietCategory(String entity){
        // implementare
        return List.of();
    }
    
    protected String getId(Dish dish){
        return dish.getId();
    }

}
