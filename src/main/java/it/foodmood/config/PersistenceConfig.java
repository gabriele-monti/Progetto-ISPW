package it.foodmood.config;

import it.foodmood.persistence.ConnectionPool;
import it.foodmood.persistence.PersistenceFactory;
import it.foodmood.persistence.PersistenceFactoryProvider;

public class PersistenceConfig {

    private PersistenceConfig(){}

    // Inizializza il pool di connessioni al database, chiamato solo quando si usa PersistenceMode.FULL
    public static void initializeDatabase(String url, String user, String pass){
        ConnectionPool.initialize(url, user, pass);
    }

    public static PersistenceFactory factory(PersistenceMode mode){
        if(mode == PersistenceMode.FULL && !ConnectionPool.isInitialized()){
            throw new IllegalStateException("Database non inizializzato correttamente!");
        }
        return PersistenceFactoryProvider.getFactory(mode);
    }
}
