package it.foodmood.persistence.dao;

import it.foodmood.config.PersistenceMode;

public abstract class DaoFactory {

    private static DaoFactory instance;

    public static synchronized void init(PersistenceMode mode){
        if(instance != null) return; // DaoFactory già inizializzata
        switch(mode){
            case FULL:
                instance = new JdbcDaoFactory();
                break;
            case DEMO:
                instance = new InMemoryDaoFactory();
                break;
            case FILESYSTEM:
                instance = new FileSystemDaoFactory();
                break;
            default: 
                instance = new InMemoryDaoFactory();
        }
    }

    public static synchronized DaoFactory getInstance(){
        if(instance == null){
            throw new IllegalStateException("DaoFactory non inizializzata. Chiama DaoFactory.init(mode)");
        }
        return instance;
    }
    
    public abstract DishDao getDishDao();
    
    public abstract UserDao getUserDao();

    public abstract CredentialDao getCredentialDao();

}
