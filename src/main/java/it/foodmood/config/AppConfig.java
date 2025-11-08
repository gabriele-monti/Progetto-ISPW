package it.foodmood.config;

public interface AppConfig {
    PersistenceMode getPersistenceMode();
    String getDbUrl();
    String getDbUser();
    String getDbPass();
}
