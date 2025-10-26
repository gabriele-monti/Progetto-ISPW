package it.foodmood.persistence.inmemory;

import it.foodmood.persistence.PersistenceFactory;
import it.foodmood.persistence.dao.DishDAO;

public class InMemoryPersistenceFactory implements PersistenceFactory {

    @Override
    public DishDAO getDishDAO(){
        return new InMemoryDishDAO();
    }
    
}
