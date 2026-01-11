package it.foodmood.persistence.dao;

import it.foodmood.persistence.filesystem.FileSystemDishDao;
import it.foodmood.persistence.filesystem.FileSystemIngredientDao;
import it.foodmood.persistence.filesystem.FileSystemOrderDao;
import it.foodmood.persistence.filesystem.FileSystemRestaurantRoomDao;
import it.foodmood.persistence.filesystem.FileSystemTableSessionDao;
import it.foodmood.persistence.filesystem.FileSystemUserDao;
import it.foodmood.persistence.filesystem.FileSystemCredentialDao;



public final class FileSystemDaoFactory extends DaoFactory {
    @Override
    public DishDao getDishDao(){ 
        return FileSystemDishDao.getInstance();
    }

    @Override
    public UserDao getUserDao(){ 
        return FileSystemUserDao.getInstance();
    }

    @Override
    public CredentialDao getCredentialDao(){ 
        return FileSystemCredentialDao.getInstance();
    }

    @Override
    public IngredientDao getIngredientDao(){ 
        return FileSystemIngredientDao.getInstance();
    }

    @Override
    public RestaurantRoomDao getRestaurantRoomDao(){ 
        return FileSystemRestaurantRoomDao.getInstance();
    }

    @Override
    public OrderDao getOrderDao(){
        return FileSystemOrderDao.getInstance();
    }

    @Override
    public TableSessionDao getTableSessionDao(){
        return FileSystemTableSessionDao.getInstance();
    }
}
