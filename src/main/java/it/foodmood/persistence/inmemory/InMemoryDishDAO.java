package it.foodmood.persistence.inmemory;

import java.util.List;
import java.util.stream.Collectors;

import it.foodmood.domain.model.Dish;
import it.foodmood.persistence.dao.DishDao;

public class InMemoryDishDao extends AbstractInMemoryCrudDao<Dish, String> implements DishDao {

    private static InMemoryDishDao instance;

    private InMemoryDishDao(){
        // costruttore per il singleton
    }

    public static synchronized InMemoryDishDao getInstance(){
        if(instance == null){
            instance = new InMemoryDishDao();
        }
        return instance;
    }

    @Override
    protected String getId(Dish dish){
        return dish.getName();
    }

    @Override
    public List<Dish> findByCategory(String category){
        if(category == null){
            return List.of();
        }
        return storage.values().stream().filter(d -> d.getCourseType().name().equalsIgnoreCase(category)).collect(Collectors.toList());
    }

    @Override
    public List<Dish> findByDietCategory(String dietCategory){
        if(dietCategory == null){
            return List.of();
        }
        return storage.values().stream().filter(d -> d.getDietCategory().name().equalsIgnoreCase(dietCategory)).collect(Collectors.toList());
    }
}
