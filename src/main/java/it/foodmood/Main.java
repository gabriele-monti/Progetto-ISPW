package it.foodmood;

import it.foodmood.config.ApplicationConfig;
import it.foodmood.config.ApplicationEnvironment;
import it.foodmood.config.PersistenceConfig;
import it.foodmood.config.StartupConfigurator;
import it.foodmood.config.StartupEnvironment;
import it.foodmood.infrastructure.bootstrap.ApplicationBootstrap;
import it.foodmood.infrastructure.bootstrap.BootstrapFactory;
import it.foodmood.infrastructure.bootstrap.UiMode;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.infrastructure.io.InputReader;
import it.foodmood.infrastructure.io.console.ConsoleInputReader;
import it.foodmood.infrastructure.io.console.ConsoleOutputWriter;
import it.foodmood.infrastructure.util.ConnectionVerifier;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.setup.InteractiveSetup;
import it.foodmood.config.PersistenceMode;
import it.foodmood.config.PersistenceSettings;

public final class Main{
    
    public static void main(String[] args){
        OutputWriter out = new ConsoleOutputWriter();

        try{
            // 1) Config dal file
            ApplicationConfig fileConfig = ApplicationConfig.fromClasspath();

            // 2) Scelta UI e Persistenza
            StartupEnvironment startup;

            boolean interactive = (args == null || args.length == 0);

            if(interactive){
                InputReader in = ConsoleInputReader.getInstance();
                startup = InteractiveSetup.askUser(in, out);
            } else {
                startup = StartupConfigurator.fromArgsAndEnvironment(args, fileConfig);
            }

            UiMode uiMode = startup.getUiMode();
            PersistenceMode persistenceMode = startup.getPersistenceMode();

            // 3) Inizializzazione della persistenza
            PersistenceSettings settings;
            PersistenceConfig persistenceConfig;

            if(persistenceMode == PersistenceMode.FULL){
                final String dbUrl = fileConfig.getDbUrl();
                final String dbUser = fileConfig.getDbUser();
                final String dbPass = fileConfig.getDbPass();

                settings = new PersistenceSettings(PersistenceMode.FULL, dbUrl, dbUser, dbPass);
                persistenceConfig = new PersistenceConfig(settings);

                boolean connected =  ConnectionVerifier.verifyWithRetry(persistenceConfig.getProvider(), 5, 5);
                // boolean connected = true;
                
                if(connected) {
                    out.println("Connessione al database verificata con successo!");
                    out.println("Database inizializzato correttamente\nURL: " + dbUrl);
                } else {
                    out.println("Impossibile stabilire la connessione al database!");
                    System.exit(1);
                }
            } else {
                out.println("Modalità demo inizializzata correttamente.\nApplicazione in memoria volatile.\n\n");
                
                settings = new PersistenceSettings(PersistenceMode.DEMO, null, null, null);
                persistenceConfig = new PersistenceConfig(settings);
            }
            
            // 4) Costruzione con factory
            DaoFactory.init(settings.mode());
            DaoFactory daoFactory = DaoFactory.getInstance();

            // 5) Costruzione dell'ambiente dell'applicazione
            ApplicationEnvironment environment = new ApplicationEnvironment(fileConfig, daoFactory);

            // 6) Bootstrap e avvio
            BootstrapFactory bootstrapFactory = new BootstrapFactory();
            ApplicationBootstrap bootstrap = bootstrapFactory.create(uiMode);
            bootstrap.start(environment);
        } catch (Exception e){
            System.err.println("Errore durante l'avvio dell'applicazione: " + e.getMessage());
            System.exit(1);
        }
    }
}
