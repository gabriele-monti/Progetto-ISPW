package it.foodmood.config;

import it.foodmood.infrastructure.bootstrap.UiMode;

public final class StartupEnvironment {
    private final UiMode uiMode;
    private final PersistenceMode persistenceMode;

    private StartupEnvironment(Builder build){
        this.uiMode = build.uiMode;
        this.persistenceMode = build.persistenceMode;

        if(uiMode == null || persistenceMode == null){
            throw new IllegalArgumentException("Interfaccia utente e modalità di persistenza obbligatori.");
        }
    }

    public UiMode getUiMode() { return uiMode;}
    public PersistenceMode getPersistenceMode() {return persistenceMode;}

    public static class Builder{
        private UiMode uiMode;
        private PersistenceMode persistenceMode;

        public Builder uiMode(UiMode mode){
            this.uiMode = mode;
            return this;
        }

        public Builder persistenceMode(PersistenceMode mode){
            this.persistenceMode = mode;
            return this;
        }

        public StartupEnvironment build(){
            return new StartupEnvironment(this);
        }
    }

}
