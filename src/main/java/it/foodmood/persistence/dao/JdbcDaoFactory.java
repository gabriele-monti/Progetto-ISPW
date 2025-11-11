package it.foodmood.persistence.dao;

import it.foodmood.persistence.mysql.JdbcDishDao;

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


}