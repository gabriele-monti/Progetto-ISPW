package it.foodmood.infrastructure.bootstrap;

import it.foodmood.config.ApplicationEnvironment;

public interface ApplicationBootstrap {
    void start(ApplicationEnvironment environment);

    default void shutdown(){
        // da implementare
    }
}
