// package it.foodmood;

// import it.foodmood.config.ApplicationConfig;
// import it.foodmood.config.ApplicationEnvironment;
// import it.foodmood.config.PersistenceConfig;
// import it.foodmood.config.PersistenceMode;
// import it.foodmood.config.PersistenceSettings;
// import it.foodmood.config.StartupEnvironment;
// import it.foodmood.config.UserMode;
// import it.foodmood.infrastructure.bootstrap.ApplicationBootstrap;
// import it.foodmood.infrastructure.bootstrap.BootstrapFactory;
// import it.foodmood.infrastructure.bootstrap.UiMode;
// import it.foodmood.infrastructure.io.InputReader;
// import it.foodmood.infrastructure.io.OutputWriter;
// import it.foodmood.infrastructure.io.console.ConsoleInputReader;
// import it.foodmood.infrastructure.io.console.ConsoleOutputWriter;
// import it.foodmood.infrastructure.util.ConnectionVerifier;
// import it.foodmood.persistence.dao.DaoFactory;
// import it.foodmood.setup.InteractiveSetup;
// import it.foodmood.view.ui.RegistrationView;
// import it.foodmood.view.ui.LoginView;
// import it.foodmood.view.ui.UiFactory;
// import it.foodmood.view.ui.cli.ConsoleView;
// import it.foodmood.view.ui.theme.AnsiUiTheme;
// import javafx.application.Application;

// public final class MainTest {
    
//     public static void main(String[] args) {
//         InputReader in = ConsoleInputReader.getInstance();
//         OutputWriter out = new ConsoleOutputWriter();
//         AnsiUiTheme theme = new AnsiUiTheme();

//         try{
//             // 1. Configurazione
//             ApplicationConfig fileConfig = ApplicationConfig.fromClasspath();
//             UserMode userMode = fileConfig.getUserMode();

//             // 2. Scelta runtime
//             boolean interactive = (args == null || args.length == 0);
//             StartupEnvironment startup;

//             if(interactive) {
//                 ConsoleView ui = new ConsoleView(in, out, theme);
//                 startup = InteractiveSetup.askUser(in, out, ui);
//             } else {
//                 String cliArg = (args.length > 0) ? args[0] : null;
//                 String envArg = System.getenv("FOODMOOD_UI");
//                 UiMode uiMode = UiMode.parse(cliArg != null ? cliArg : envArg, UiMode.GUI);

//                 PersistenceMode persistenceMode = fileConfig.getPersistenceMode();

//                 startup = new StartupEnvironment(uiMode, persistenceMode);
//             }

//             UiFactory.init(startup.getUiMode(), userMode);

//             // 3. Inizializzazione della persistenza
//             PersistenceSettings settings;
//             PersistenceConfig persistenceConfig;

//             if(startup.getPersistenceMode() == PersistenceMode.FULL){
//                 final String dbUrl = fileConfig.getDbUrl();
//                 final String dbUser = fileConfig.getDbUser();
//                 final String dbPass = fileConfig.getDbPass();

//                 settings = new PersistenceSettings(PersistenceMode.FULL, dbUrl, dbUser, dbPass);
//                 persistenceConfig = new PersistenceConfig(settings);

//                 boolean connected =  ConnectionVerifier.verifyWithRetry(persistenceConfig.getProvider(), 5, 5);
//                 // boolean connected = true;


//                 if(connected) {
//                     out.println("Connessione al database verificata con successo!");
//                     out.println("Database inizializzato correttamente\nURL: " + dbUrl);
//                 } else {
//                     out.println("Impossibile stabilire la connessione al database!");
//                     System.exit(1);
//                 }
//             } else {
//                 out.println("Modalità demo inizializzata correttamente.\nApplicazione in memoria volatile.\n\n");
                
//                 settings = new PersistenceSettings(PersistenceMode.DEMO, null, null, null);
//                 persistenceConfig = new PersistenceConfig(settings);
//             }
            
//             // 4. Costruzione con factory
//             DaoFactory.init(settings.mode());
//             DaoFactory daoFactory = DaoFactory.getInstance();

//             // 5. Costruzione dell'ambiente dell'applicazione
//             ApplicationEnvironment environment = new ApplicationEnvironment(fileConfig, daoFactory);

//             if(startup.getUiMode() == UiMode.CLI){
//                 RegistrationView registrationView = UiFactory.getInstance().createRegistrationView();
//                 LoginView loginView = UiFactory.getInstance().createLoginView();

//                 out.print("Hai già un account? (s/n): ");
//                 String response = in.readLine();

//                 if("n".equalsIgnoreCase(response.trim())){
//                     registrationView.show();
//                 }
//                 loginView.show();
//             } else if (startup.getUiMode() == UiMode.GUI){
//                 Application.launch(FoodMoodGui.class, args);
//             }

//             // 6. Bootstrap e avvio
//             BootstrapFactory bootstrapFactory = new BootstrapFactory(out);
//             ApplicationBootstrap bootstrap = bootstrapFactory.create(startup.getUiMode());
//             bootstrap.start(environment);

//         } catch (Exception e){
//             System.err.println("Errore durante l'avvio dell'applicazione: " + e.getMessage());
//             System.exit(1);
//         }
//     }
// }
