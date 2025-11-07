package it.foodmood;

import it.foodmood.config.ApplicationConfig;
import it.foodmood.persistence.dao.DaoFactory;

public record ApplicationEnvironment(ApplicationConfig config, DaoFactory daoFactory) {

    public ApplicationEnvironment{
        if(config == null || daoFactory == null){
            throw new IllegalArgumentException("I parametri di configurazione e di persistenza non possono essere nulli.");
        }
    }
}
