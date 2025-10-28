package it.foodmood.setup;

import it.foodmood.StartupEnvironment;
import it.foodmood.config.PersistenceMode;
import it.foodmood.infrastructure.bootstrap.UiMode;

import java.util.Scanner;

public final class InteractiveSetup {

    public static StartupEnvironment askUser(Scanner scanner){
        UiMode ui = askUiMode(scanner);
        PersistenceMode pm = askPersistenceMode(scanner);

        StartupEnvironment.Builder build = new StartupEnvironment.Builder().uiMode(ui).persistenceMode(pm);

        if(pm == PersistenceMode.FULL){
            System.out.print("Database URL (es: jdbc:mysql://localhost:3306/foodmood): ");
            String url = scanner.nextLine().trim();
            System.out.print("Database User: ");
            String user = scanner.nextLine().trim();
            System.out.print("Database Password: ");
            String pass = scanner.nextLine().trim();
            build.db(url, user, pass);
        }

        return build.build();
    }

    private static UiMode askUiMode(Scanner scanner){
        while(true){
            System.out.println("Seleziona interfaccia: ");
            System.out.println(" 1) CLI");
            System.out.println(" 2) GUI");
            System.out.println("Scelta [1,2]: ");
            String input = scanner.nextLine().trim();
            if("1".equals(input)) return UiMode.CLI;
            if("2".equals(input)) return UiMode.GUI;
            System.out.println("Scelta non valida.\n");
        }
    }

    private static PersistenceMode askPersistenceMode(Scanner scanner){
        while(true){
            System.out.println("Seleziona persistenza: ");
            System.out.println(" 1) DEMO (In-Memory)");
            System.out.println(" 2) FULL (MySQL)");
            System.out.println("Scelta [1,2]: ");
            String input = scanner.nextLine().trim();
            if("1".equals(input)) return PersistenceMode.DEMO;
            if("2".equals(input)) return PersistenceMode.FULL;
            System.out.println("Scelta non valida.\n");
        }
    }

}
