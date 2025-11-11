package it.foodmood.persistence.dao;

import it.foodmood.persistence.inmemory.InMemoryDishDao;

public final class InMemoryDaoFactory extends DaoFactory {

    @Override
    public DishDao getDishDao(){
        return InMemoryDishDao.getInstance();
    } 
    
    @Override
    public UserDao getUserDao(){
        return InMemoryUserDao.getInstance();
    }   
}
