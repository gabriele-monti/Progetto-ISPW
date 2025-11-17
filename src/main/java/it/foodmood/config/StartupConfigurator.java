package it.foodmood.config;

import it.foodmood.infrastructure.bootstrap.UiMode;

public final class StartupConfigurator {
    
    private StartupConfigurator(){
        //costruttore vuoto
    }

    /*
     * Decide UiMode e PersistenceMode in base ad:
     * - args nella riga di comando
     * - variabili di ambiente
     * - application.properties
     */

     public static StartupEnvironment fromArgsAndEnvironment(String[] args, ApplicationConfig config){
        String cliUi = (args != null && args.length > 0) ? args[0] : null;
        String cliPersistence = (args != null && args.length > 1) ? args[1] : null;
        
        String envUi = System.getenv("FOODMOOD_UI");
        String envPersistence = System.getenv("FOODMOOD_PERSISTENCE");

        UiMode uiMode = UiMode.parse(cliUi != null ? cliUi : envUi, UiMode.GUI);


        PersistenceMode persistenceMode;

        if(cliPersistence != null){
            persistenceMode = PersistenceMode.fromValue(cliPersistence);
        } else if (envPersistence != null){
            persistenceMode = PersistenceMode.fromValue(envPersistence);
        } else {
            persistenceMode = config.getPersistenceMode();
        }
        
        return new StartupEnvironment(uiMode, persistenceMode);
     }
}
