package it.foodmood.persistence.filesystem;

import java.util.List;
import java.util.Optional;

import it.foodmood.domain.model.Dish;
import it.foodmood.persistence.dao.DishDao;

public class FileSystemDishDao implements DishDao {
    private static FileSystemDishDao instance;

    public static synchronized FileSystemDishDao getInstance(){
        if(instance == null){
            instance = new FileSystemDishDao();
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
}
