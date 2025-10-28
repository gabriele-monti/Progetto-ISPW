package it.foodmood;

import it.foodmood.config.PersistenceMode;
import it.foodmood.infrastructure.bootstrap.UiMode;

public final class StartupEnvironment {
    private final UiMode uiMode;
    private final PersistenceMode persistenceMode;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPass;

    private StartupEnvironment(Builder build){
        this.uiMode = build.uiMode;
        this.persistenceMode = build.persistenceMode;
        this.dbUrl = build.dbUrl;
        this.dbUser = build.dbUser;
        this.dbPass = build.dbPass;

        if(uiMode == null || persistenceMode == null){
            throw new IllegalArgumentException("Interfaccia utente e modalità di persistenza obbligatori.");
        }
    }

    public UiMode getUiMode() { return uiMode;}
    public PersistenceMode getPersistenceMode() {return persistenceMode;}
    public String getDbUrl() {return dbUrl;}
    public String getDbUser() {return dbUser;}
    public String getDbPass() {return dbPass;}

    public static class Builder{
        private UiMode uiMode;
        private PersistenceMode persistenceMode;
        private String dbUrl;
        private String dbUser;
        private String dbPass;

        public Builder uiMode(UiMode mode){
            this.uiMode = mode;
            return this;
        }

        public Builder persistenceMode(PersistenceMode mode){
            this.persistenceMode = mode;
            return this;
        }

        public Builder db(String url, String user, String pass){
            this.dbUrl = url;
            this.dbUser = user;
            this.dbPass = pass;
            return this;
        }

        public StartupEnvironment build(){
            return new StartupEnvironment(this);
        }
    }

}
