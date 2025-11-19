package it.foodmood.persistence.dao;

import it.foodmood.persistence.filesystem.FileSystemDishDao;
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
}
