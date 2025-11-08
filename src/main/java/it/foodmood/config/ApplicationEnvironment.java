package it.foodmood.config;

import it.foodmood.persistence.dao.DaoFactory;

public record ApplicationEnvironment(ApplicationConfig config, DaoFactory daoFactory) {

    public ApplicationEnvironment (ApplicationConfig config, DaoFactory daoFactory){
        if(config == null || daoFactory == null){
            throw new IllegalArgumentException("I parametri di configurazione e di persistenza non possono essere nulli.");
        }
        this.config = config;
        this.daoFactory = daoFactory;
    }
}
