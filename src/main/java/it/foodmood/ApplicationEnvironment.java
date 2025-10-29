package it.foodmood;

import it.foodmood.config.ApplicationConfig;
import it.foodmood.persistence.PersistenceFactory;

public record ApplicationEnvironment(ApplicationConfig config, PersistenceFactory persistenceFactory ) {

    public ApplicationEnvironment{
        if(config == null || persistenceFactory == null){
            throw new IllegalArgumentException("I parametri di configurazione e di persistenza non possono essere nulli.");
        }
    }
}
