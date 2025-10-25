package it.foodmood.persistence;

import it.foodmood.config.PersistenceMode;
import it.foodmood.persistence.inmemory.InMemoryPersistenceFactory;
import it.foodmood.persistence.mysql.MySqlPersistenceFactory;

public class PersistenceFactoryProvider {

    private PersistenceFactoryProvider(){}

    // Restituisce la factory appropriata per la modalità di persistenza
    public static PersistenceFactory getFactory(PersistenceMode mode){
        return switch (mode){
            case DEMO -> new InMemoryPersistenceFactory();
            case FULL -> new MySqlPersistenceFactory(ConnectionPool.getDataSource());
        };
    }
}
