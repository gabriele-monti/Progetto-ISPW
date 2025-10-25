package it.foodmood.persistence.mysql;

import javax.sql.DataSource;

import it.foodmood.persistence.ConnectionProvider;
import it.foodmood.persistence.DataSourceConnectionProvider;
import it.foodmood.persistence.PersistenceFactory;
import it.foodmood.persistence.dao.DishDAO;

/*
 * Implementazione concreta della factory per la persistenza su MySQL
 */

public class MySqlPersistenceFactory implements PersistenceFactory {
    
    // DAO concreti

    private final DishDAO dishDAO;

    // Costruttore riceve il DataSource già inizializzato dal ConnectionPool

    public MySqlPersistenceFactory(DataSource dataSource){
        ConnectionProvider provider = new DataSourceConnectionProvider(dataSource);

        // Costruzione dei DAO conreti
        this.dishDAO = new JdbcDishDAO(provider);
    }

    @Override
    public DishDAO getDishDAO(){ 
        return dishDAO;
    }
}
