package it.foodmood.config;

import java.util.Properties;

public final class ApplicationConfig implements AppConfig{

    private final Properties properties;

    private ApplicationConfig(Properties properties) {
        this.properties = properties;
    }

    public static ApplicationConfig fromClasspath(){
        return fromClasspath(new ClasspathPropertiesLoader());
    }

    public static ApplicationConfig fromClasspath(PropertiesLoader loader){
        return new ApplicationConfig(loader.load("database.properties"));
    }

    @Override
    public PersistenceMode getPersistenceMode(){
        String mode = properties.getProperty(Keys.MODE, "demo").trim();
        return PersistenceMode.fromValue(mode);
    }

    private String require(String key){
        String s = properties.getProperty(key);
        if(s == null || s.isBlank()){
            throw new IllegalStateException("Manca la property: " + key);
        }
        return s.trim();
    }

    public String getDbUrl() { return require(Keys.DB_URL); }
    public String getDbUser(){ return require(Keys.DB_USER);}
    public String getDbPass(){ return require(Keys.DB_PASS);}

}
