package it.foodmood.persistence.dao;

import it.foodmood.persistence.mysql.JdbcDishDao;
import it.foodmood.persistence.mysql.JdbcIngredientDao;
import it.foodmood.persistence.mysql.JdbcRestaurantRoomDao;
import it.foodmood.persistence.mysql.JdbcUserDao;
import it.foodmood.persistence.mysql.JdbcCredentialDao;

/*
 * Implementazione concreta della factory per la persistenza su MySQL
 */

public final class JdbcDaoFactory extends DaoFactory {

    @Override
    public DishDao getDishDao(){ 
        return JdbcDishDao.getInstance();
    }

    @Override
    public UserDao getUserDao(){ 
        return JdbcUserDao.getInstance();
    }

    @Override
    public CredentialDao getCredentialDao(){ 
        return JdbcCredentialDao.getInstance();
    }

    @Override
    public IngredientDao getIngredientDao(){ 
        return JdbcIngredientDao.getInstance();
    }

    @Override
    public RestaurantRoomDao getRestaurantRoomDao(){ 
        return JdbcRestaurantRoomDao.getInstance();
    }
}