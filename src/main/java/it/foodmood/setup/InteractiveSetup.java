package it.foodmood.setup;

import it.foodmood.config.PersistenceMode;
import it.foodmood.config.StartupEnvironment;
import it.foodmood.infrastructure.bootstrap.UiMode;
import it.foodmood.view.ui.cli.ConsoleView;
import it.foodmood.view.ui.theme.UiTheme;


public final class InteractiveSetup extends ConsoleView {

    public InteractiveSetup(UiTheme theme){
        super(theme);
    }

    public StartupEnvironment askUser(){
        clearScreen();
        showInfo(theme.bold("Benvenuto nella configurazione di FoodMood\n"));
        UiMode uiMode = askUiMode();
        PersistenceMode pm = askPersistenceMode();

        return new StartupEnvironment(uiMode, pm);
    }

    private UiMode askUiMode(){
        while(true){
            out.displayTitle("Seleziona Interfaccia");
            showInfo(theme.success("1. CLI ") + theme.info("-> Interfaccia a riga di comando"));
            showInfo(theme.success("2. GUI ") + theme.info("-> Interfaccia grafica"));
            out.print("\nScelta: ");
            String input = in.readLine().trim();
            if("1".equals(input)) return UiMode.CLI;
            if("2".equals(input)) return UiMode.GUI;
            clearScreen();
            showWarning("Scelta non valida.\n");
        }
    }

    private PersistenceMode askPersistenceMode(){
        while(true){
            showTitle("Seleziona Modalità di Persistenza");
            showInfo("\nLa modalità determina dove vengono salvati i dati\n");
            showInfo(theme.success("1. DEMO        ") + theme.info("-> Nessun salvataggio (in-memory)"));
            showInfo(theme.success("2. FILESYSTEM  ") + theme.info("-> CSV locali in /data/csv/"));
            showInfo(theme.success("3. FULL        ") + theme.info("-> Database MySQL)"));
            out.print("\nScelta: ");
            String input = in.readLine().trim();
            if("1".equals(input)) return PersistenceMode.DEMO;
            if("2".equals(input)) return PersistenceMode.FILESYSTEM;
            if("3".equals(input)) return PersistenceMode.FULL;
            clearScreen();
            showWarning("Scelta non valida.\n");
        }
    }
}
