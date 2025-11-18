package it.foodmood.persistence.dao;

import it.foodmood.persistence.inmemory.InMemoryDishDao;


public final class InMemoryDaoFactory extends DaoFactory {

    @Override
    public DishDao getDishDao(){
        return InMemoryDishDao.getInstance();
    } 
    
    @Override
    public UserDao getUserDao(){
        throw new UnsupportedOperationException("Non ancora implementato");
    }   

    @Override
    public CredentialDao getCredentialDao(){
        throw new UnsupportedOperationException("Non ancora implementato");
    }   
}
