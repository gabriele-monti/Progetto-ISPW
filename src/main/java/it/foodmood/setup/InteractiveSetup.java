package it.foodmood.setup;

import it.foodmood.config.PersistenceMode;
import it.foodmood.config.StartupEnvironment;
import it.foodmood.infrastructure.bootstrap.UiMode;
import it.foodmood.view.ui.cli.ConsoleView;
import it.foodmood.view.ui.theme.UiTheme;


public final class InteractiveSetup extends ConsoleView {

    public InteractiveSetup(UiTheme theme){
        super();
    }

    public StartupEnvironment askUser(){
        clearScreen();
        showTitle("FoodMood");
        showInfo("\nBenvenuto in FoodMood, procedi con la configurazione del software\n");
        UiMode uiMode = askUiMode();
        PersistenceMode pm = askPersistenceMode();

        return new StartupEnvironment(uiMode, pm);
    }

    private UiMode askUiMode(){
        while(true){
            showTitle("Seleziona Interfaccia");
            showInfo("");
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
            showInfo("");
            showInfo("\nLa modalità determina dove vengono salvati i dati\n");
            showInfo(theme.success("1. DEMO        ") + theme.info("-> Nessun salvataggio, dati in memoria volatile"));
            showInfo(theme.success("2. FILESYSTEM  ") + theme.info("-> Salvataggio in file CSV locali (cartella /data/csv/ )"));
            showInfo(theme.success("3. FULL        ") + theme.info("-> Salvataggio completo tramite database MySQL"));
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
