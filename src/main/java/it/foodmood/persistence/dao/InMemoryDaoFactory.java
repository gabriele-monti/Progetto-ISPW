package it.foodmood.persistence.dao;

import it.foodmood.persistence.inmemory.InMemoryDishDao;
import it.foodmood.persistence.inmemory.InMemoryUserDao;
import it.foodmood.persistence.inmemory.InMemoryCredentialDao;

public final class InMemoryDaoFactory extends DaoFactory {

    @Override
    public DishDao getDishDao(){
        return InMemoryDishDao.getInstance();
    } 
    
    @Override
    public UserDao getUserDao(){
        throw new UnsupportedOperationException("Non ancora implementato");
        
        // return InMemoryUserDao.getInstance();
    }   

    @Override
    public CredentialDao getCredentialDao(){
        throw new UnsupportedOperationException("Non ancora implementato");
        // return InMemoryCredentialDao.getInstance();
    }   
}
