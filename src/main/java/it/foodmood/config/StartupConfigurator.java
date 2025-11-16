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
        String cliArg = (args != null && args.length > 0) ? args[0] : null;
        String envArg = System.getenv("FOODMOOD_UI");

        UiMode uiMode = UiMode.parse(cliArg != null ? cliArg : envArg, UiMode.GUI);

        PersistenceMode persistenceMode = config.getPersistenceMode();
        
        return new StartupEnvironment(uiMode, persistenceMode);
     }
}
