package it.foodmood.setup;

import it.foodmood.StartupEnvironment;
import it.foodmood.config.PersistenceMode;
import it.foodmood.infrastructure.bootstrap.UiMode;
import it.foodmood.infrastructure.io.InputReader;
import it.foodmood.infrastructure.io.OutputWriter;

public final class InteractiveSetup {

    private InteractiveSetup(){}

    public static StartupEnvironment askUser(InputReader in, OutputWriter out){
        UiMode ui = askUiMode(in, out);
        PersistenceMode pm = askPersistenceMode(in, out);

        return new StartupEnvironment.Builder().uiMode(ui).persistenceMode(pm).build();
    }

    private static UiMode askUiMode(InputReader in, OutputWriter out){
        while(true){
            out.println("Seleziona interfaccia: ");
            out.println(" 1) CLI");
            out.println(" 2) GUI");
            out.print("Scelta: ");
            String input = in.readLine().trim();
            if("1".equals(input)) return UiMode.CLI;
            if("2".equals(input)) return UiMode.GUI;
            out.println("Scelta non valida.\n");
        }
    }

    private static PersistenceMode askPersistenceMode(InputReader in, OutputWriter out){
        while(true){
            out.println("Seleziona persistenza: ");
            out.println(" 1) DEMO (In-Memory)");
            out.println(" 2) FULL (MySQL)");
            out.print("Scelta: ");
            String input = in.readLine().trim();
            if("1".equals(input)) return PersistenceMode.DEMO;
            if("2".equals(input)) return PersistenceMode.FULL;
            out.println("Scelta non valida.\n");
        }
    }

}
