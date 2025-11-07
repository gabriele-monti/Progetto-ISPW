package it.foodmood.config;

import java.util.Objects;

import it.foodmood.persistence.ConnectionProvider;
import it.foodmood.persistence.DriverManagerConnection;

public final class PersistenceConfig {

    private final PersistenceSettings settings;
    private final ConnectionProvider provider;

    public PersistenceConfig(PersistenceSettings settings){
        this.settings = Objects.requireNonNull(settings, "Settings non può essere nullo.");

        if(settings.mode() == PersistenceMode.FULL){
            DriverManagerConnection.init(settings.url(), settings.user(), settings.pass());
            this.provider = DriverManagerConnection.getInstance();
        } else {
            this.provider = null;
        }
    }

    public PersistenceSettings settings(){
        return settings;
    }

    public ConnectionProvider provider(){
        if(provider == null){
            throw new IllegalStateException("Provider non inizializzato.");
        }
        return provider;
    }
}
