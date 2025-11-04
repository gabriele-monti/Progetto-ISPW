package it.foodmood.persistence.mysql;

import it.foodmood.persistence.ConnectionProvider;
import it.foodmood.persistence.PersistenceFactory;
import it.foodmood.persistence.dao.DishDAO;

/*
 * Implementazione concreta della factory per la persistenza su MySQL
 */

public final class MySqlPersistenceFactory implements PersistenceFactory {

    private static volatile MySqlPersistenceFactory instance;

    public static synchronized MySqlPersistenceFactory getInstance(ConnectionProvider provider){
        if(instance == null){
            if(provider == null){
                throw new IllegalArgumentException("Connection provider non può essere nullo.");
            }
            instance = new MySqlPersistenceFactory(provider);
        }
        return instance;
    }
    
    // DAO concreti

    private final DishDAO dishDAO;

    // Costruttore riceve il DataSource già inizializzato dal ConnectionPool

    private MySqlPersistenceFactory(ConnectionProvider provider){
        // Costruzione dei DAO conreti
        this.dishDAO = new JdbcDishDAO(provider);
    }

    @Override
    public DishDAO getDishDAO(){ 
        return dishDAO;
    }
}
