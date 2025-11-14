package it.foodmood.config;

import it.foodmood.infrastructure.bootstrap.UiMode;

public final class StartupEnvironment {
    private final UiMode uiMode;
    private final PersistenceMode persistenceMode;

    public StartupEnvironment(UiMode uiMode, PersistenceMode persistenceMode){
        if(uiMode == null || persistenceMode == null){
            throw new IllegalArgumentException("Interfaccia utente e modalità di persistenza obbligatori.");
        }
        
        this.uiMode = uiMode;
        this.persistenceMode = persistenceMode;
    }

    public UiMode getUiMode() { return uiMode;}
    public PersistenceMode getPersistenceMode() {return persistenceMode;}
}
