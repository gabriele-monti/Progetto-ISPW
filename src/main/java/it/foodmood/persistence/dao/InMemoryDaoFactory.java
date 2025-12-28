package it.foodmood.persistence.dao;

import it.foodmood.persistence.inmemory.InMemoryDishDao;
import it.foodmood.persistence.inmemory.InMemoryIngredientDao;
import it.foodmood.persistence.inmemory.InMemoryOrderDao;
import it.foodmood.persistence.inmemory.InMemoryRestaurantRoomDao;
import it.foodmood.persistence.inmemory.InMemoryTableSessionDao;
import it.foodmood.persistence.inmemory.InMemoryUserDao;
import it.foodmood.persistence.inmemory.InMemoryCredentialDao;

public final class InMemoryDaoFactory extends DaoFactory {

    @Override
    public DishDao getDishDao(){
        return InMemoryDishDao.getInstance();
    } 
    
    @Override
    public UserDao getUserDao(){
        return InMemoryUserDao.getInstance();
    }   

    @Override
    public CredentialDao getCredentialDao(){
        return InMemoryCredentialDao.getInstance();
    }   

    @Override
    public IngredientDao getIngredientDao(){
        return InMemoryIngredientDao.getInstance();
    }  
    
    @Override
    public RestaurantRoomDao getRestaurantRoomDao(){ 
        return InMemoryRestaurantRoomDao.getInstance();
    }

    @Override
    public OrderDao getOrderDao(){
        return InMemoryOrderDao.getInstance();
    }

    @Override
    public TableSessionDao getTableSessionDao(){
        return InMemoryTableSessionDao.getInstance();
    }
}
