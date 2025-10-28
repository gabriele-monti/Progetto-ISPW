package it.foodmood;

import java.util.Scanner;

import it.foodmood.config.ApplicationConfig;
import it.foodmood.config.PersistenceConfig;
import it.foodmood.config.PersistenceMode;
import it.foodmood.infrastructure.bootstrap.ApplicationBootstrap;
import it.foodmood.infrastructure.bootstrap.BootstrapFactory;
import it.foodmood.infrastructure.bootstrap.UiMode;
import it.foodmood.persistence.PersistenceFactory;
import it.foodmood.setup.InteractiveSetup;

public final class Main {
    
    public static void main(String[] args) {
        try{
            // 1. Configurazione
            ApplicationConfig fileConfig = ApplicationConfig.loadFromClasspath();

            // 2. Scelta runtime
            boolean interactive = (args == null || args.length == 0);
            StartupEnvironment startup;

            if(interactive){
                try(Scanner scan = new Scanner(System.in)){
                    startup = InteractiveSetup.askUser(scan);
                }
            } else {
                String cliArg = (args.length > 0) ? args[0] : null;
                String envArg = System.getenv("FOODMOOD_UI");
                UiMode uiMode = UiMode.parse(cliArg != null ? cliArg : envArg, UiMode.GUI);

                PersistenceMode persistenceMode = fileConfig.getPersistenceMode();

                startup = new StartupEnvironment.Builder()
                              .uiMode(uiMode)
                              .persistenceMode(persistenceMode)
                              .db(fileConfig.getDbUrl(), fileConfig.getDbUser(), fileConfig.getDbPass())
                              .build();
            }

            // 3. Inizializzazione della persistenza

            if(startup.getPersistenceMode() == PersistenceMode.FULL){
                PersistenceConfig.initializeDatabase(startup.getDbUrl(), startup.getDbUser(), startup.getDbPass());
                System.out.println("Database inizializzato correttamente\nURL: " + startup.getDbUrl());
            } else {
                System.out.println("Modalità demo inizializzata correttamente.\nApplicazione in memoria volatile.\n\n");
            }
            
            // 4. Costruzione con factory
            PersistenceFactory factory = PersistenceConfig.factory(startup.getPersistenceMode());

            ApplicationEnvironment environment = new ApplicationEnvironment(fileConfig, factory);

            ApplicationBootstrap bootstrap = BootstrapFactory.create(startup.getUiMode());
            bootstrap.start(environment);

            // 3. Interfaccia utente

            // 4. Costruzione del ambiente software

            // 5. Bootstrap Factory dal pattern GoF

        } catch (Exception e){
            System.err.println("Errore durante l'avvio dell'applicazione: " + e.getMessage());
            System.exit(1);
        }
    }
}
