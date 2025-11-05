package it.foodmood.persistence.mysql;

import it.foodmood.persistence.ConnectionProvider;
import it.foodmood.persistence.PersistenceFactory;
import it.foodmood.persistence.dao.DishDAO;

/*
 * Implementazione concreta della factory per la persistenza su MySQL
 */

public final class MySqlPersistenceFactory implements PersistenceFactory {

    private static volatile MySqlPersistenceFactory instance;

    private final ConnectionProvider provider;
    private final DishDAO dishDAO;

    private MySqlPersistenceFactory(ConnectionProvider provider){
        this.provider = provider;
        this.dishDAO = new JdbcDishDAO(provider);
    }

    public static synchronized MySqlPersistenceFactory getInstance(ConnectionProvider provider){
        if(provider == null){
            throw new IllegalArgumentException("Il provider non può essere nullo.");
        }
        if(instance == null){
            instance = new MySqlPersistenceFactory(provider);
        } else if (instance.provider != provider){
            throw new IllegalStateException("Factory già inizializzata con un altro provider.");
        }
        return instance;
    }

    @Override
    public DishDAO getDishDAO(){ 
        return dishDAO;
    }
}