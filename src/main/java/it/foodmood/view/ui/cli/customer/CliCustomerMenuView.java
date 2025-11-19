package it.foodmood.view.ui.cli.customer;

import it.foodmood.view.ui.cli.CliPages;
import it.foodmood.view.ui.cli.ConsoleView;

public class CliCustomerMenuView extends ConsoleView {

    public CliCustomerMenuView(){
        super();
    }

    @Override
    public CliPages displayPage(){
        while(true){
            // clearScreen();
            showTitle("Menù Cliente");

            showInfo("1. Login");
            showInfo("2. Registrazione");
            showInfo("3. Esci");

            String choice = askInput("\nSeleziona un'opzione: ");

            switch(choice){
                case "1": return CliPages.LOGIN;
                case "2": return CliPages.REGISTRATION;
                case "3": return CliPages.EXIT;
                default : showError("Scelta non valida, riprova.");
            }
        }
    }
}
