package it.foodmood;

import it.foodmood.config.ApplicationConfig;
import it.foodmood.persistence.PersistenceFactory;

public final class ApplicationEnvironment {

    private final ApplicationConfig config;
    private final PersistenceFactory persistenceFactory;

    public ApplicationEnvironment(ApplicationConfig config, PersistenceFactory persistenceFactory){
        if(config == null || persistenceFactory == null){
            throw new IllegalArgumentException("I parametri di configurazione e di persistenza non possono essere nulli.");
        }
        this.config=config;
        this.persistenceFactory=persistenceFactory;
    }

    public ApplicationConfig config() {return config;}
    public PersistenceFactory persistenceFactory() { return persistenceFactory;}
}
