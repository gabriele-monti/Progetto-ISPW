package it.foodmood.persistence.inmemory;

import it.foodmood.persistence.PersistenceFactory;
import it.foodmood.persistence.dao.DishDAO;

public final class InMemoryPersistenceFactory implements PersistenceFactory {

    private InMemoryPersistenceFactory() {}

    private static class FactoryHolder{
        private static final InMemoryPersistenceFactory instance = new InMemoryPersistenceFactory();
    }

    public static InMemoryPersistenceFactory getInstance(){
        return FactoryHolder.instance;
    }

    private static class DishDaoHolder{
        private static final DishDAO instance = new InMemoryDishDAO();
    }

    @Override
    public DishDAO getDishDAO(){
        return DishDaoHolder.instance;
    }
    
}
