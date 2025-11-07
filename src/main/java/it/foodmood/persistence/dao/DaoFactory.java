package it.foodmood.persistence.dao;

import it.foodmood.config.PersistenceMode;

public abstract class DaoFactory {

    private static volatile DaoFactory instance = null;

    public static void init(PersistenceMode mode){
        if(instance != null) return; // DaoFactory già inizializzata
        synchronized (DaoFactory.class){
            if(instance == null){
                switch (mode) {
                    case FULL:
                        instance = new JdbcDaoFactory();
                        break;
                    case DEMO:
                        instance = new InMemoryDaoFactory();
                        break;
                    default: 
                        instance = new InMemoryDaoFactory();
                }
            }
        }
    }

    public static DaoFactory getInstance(){
        if(instance == null){
            throw new IllegalStateException("DaoFactory non inizializzata. Chiama DaoFactory.init(mode)");
        }
        return instance;
    }
    
    public abstract DishDao getDishDao();
}
