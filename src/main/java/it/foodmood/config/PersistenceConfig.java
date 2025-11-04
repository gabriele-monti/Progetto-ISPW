package it.foodmood.config;

import it.foodmood.persistence.ConnectionProvider;
import it.foodmood.persistence.DriverManagerConnectionProvider;
import it.foodmood.persistence.PersistenceFactory;
import it.foodmood.persistence.PersistenceFactoryProvider;

public class PersistenceConfig {

    private PersistenceConfig(){}

    private static ConnectionProvider provider;

    // Inizializza il pool di connessioni al database, chiamato solo quando si usa PersistenceMode.FULL
    public static void initializeDatabase(String url, String user, String pass){
        provider = new DriverManagerConnectionProvider(url, user, pass);
    }

    public static PersistenceFactory factory(PersistenceMode mode){
        if(mode == PersistenceMode.FULL && provider == null){
            throw new IllegalStateException("Database non inizializzato correttamente!");
        }
        return PersistenceFactoryProvider.getFactory(mode, provider);
    }

    public static ConnectionProvider getProvider(){
        if(provider == null){
            throw new IllegalStateException("Il provider non è inizializzato!");
        }
        return provider;
    }
}
