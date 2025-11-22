package it.foodmood.persistence.dao;

import it.foodmood.persistence.inmemory.InMemoryDishDao;
import it.foodmood.persistence.inmemory.InMemoryIngredientDao;
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
}
