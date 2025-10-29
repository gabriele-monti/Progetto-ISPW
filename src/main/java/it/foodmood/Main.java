package it.foodmood;

import it.foodmood.config.ApplicationConfig;
import it.foodmood.config.PersistenceConfig;
import it.foodmood.config.PersistenceMode;
import it.foodmood.infrastructure.bootstrap.ApplicationBootstrap;
import it.foodmood.infrastructure.bootstrap.BootstrapFactory;
import it.foodmood.infrastructure.bootstrap.UiMode;
import it.foodmood.infrastructure.io.InputReader;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.infrastructure.io.console.ConsoleInputReader;
import it.foodmood.infrastructure.io.console.ConsoleOutputWriter;
import it.foodmood.persistence.ConnectionPool;
import it.foodmood.persistence.PersistenceFactory;
import it.foodmood.setup.InteractiveSetup;

public final class Main {
    
    public static void main(String[] args) {
        OutputWriter out = new ConsoleOutputWriter();

        try{
            // 1. Configurazione
            ApplicationConfig fileConfig = ApplicationConfig.loadFromClasspath();

            // 2. Scelta runtime
            boolean interactive = (args == null || args.length == 0);
            StartupEnvironment startup;

            if(interactive){
                try(InputReader in = new ConsoleInputReader()){
                    startup = InteractiveSetup.askUser(in, out);
                }
            } else {
                String cliArg = (args.length > 0) ? args[0] : null;
                String envArg = System.getenv("FOODMOOD_UI");
                UiMode uiMode = UiMode.parse(cliArg != null ? cliArg : envArg, UiMode.GUI);

                PersistenceMode persistenceMode = fileConfig.getPersistenceMode();

                startup = new StartupEnvironment.Builder()
                              .uiMode(uiMode)
                              .persistenceMode(persistenceMode)
                              .build();
            }

            // 3. Inizializzazione della persistenza

            if(startup.getPersistenceMode() == PersistenceMode.FULL){
                String dbUrl = fileConfig.getDbUrl();
                String dbUser = fileConfig.getDbUser();
                String dbPass = fileConfig.getDbPass();

                PersistenceConfig.initializeDatabase(dbUrl, dbUser, dbPass);
                out.println("Database inizializzato correttamente\nURL: " + dbUrl);
            } else {
                out.println("Modalità demo inizializzata correttamente.\nApplicazione in memoria volatile.\n\n");
            }
            
            // 4. Costruzione con factory
            PersistenceFactory factory = PersistenceConfig.factory(startup.getPersistenceMode());

            // 5. Costruzione dell'ambiente dell'applicazione
            ApplicationEnvironment environment = new ApplicationEnvironment(fileConfig, factory);

            // 6. Bootstrap e avvio
            ApplicationBootstrap bootstrap = BootstrapFactory.create(startup.getUiMode());
            bootstrap.start(environment);

        } catch (Exception e){
            System.err.println("Errore durante l'avvio dell'applicazione: " + e.getMessage());
            System.exit(1);
        } finally {
            if(ConnectionPool.isInitialized()){
                ConnectionPool.shutdown();
            }
        }
    }
}
