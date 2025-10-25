package it.foodmood.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationConfig {
    private final Properties properties;

    private ApplicationConfig(Properties properties) {
        this.properties = properties;
    }

    public static ApplicationConfig loadFromClasspath(){
        try(InputStream in =Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")){
            if (in == null) {
                throw new IllegalStateException("Il file 'application.properties' non trovato.");
            }
            Properties p = new Properties();
            p.load(in);
            return new ApplicationConfig(p);
        } catch (IOException e){
            throw new IllegalStateException("Errore durante la lettura del file 'application.properties'.");
        }
    }

    public PersistenceMode getPersistenceMode(){
        String mode = properties.getProperty("app.persistence.mode", "demo");
        return PersistenceMode.fromValue(mode);
    }

    private String require(String key){
        String s = properties.getProperty(key);
        if(s == null || s.isBlank()){
            throw new IllegalStateException("Manca la property: " + key);
        }
        return s.trim();
    }

    public String getDbUrl(){ return require("db.url");}
    public String getDbUser(){ return require("db.user");}
    public String getDbPass(){ return require("db.pass");}

}
