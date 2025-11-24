package it.foodmood.view.ui.cli.customer;

import it.foodmood.view.ui.cli.CustomerPages;
import it.foodmood.view.ui.cli.ConsoleView;

public class CliCustomerMenuView extends ConsoleView {

    public CliCustomerMenuView(){
        super();
    }

    public CustomerPages displayPage(){
        while(true){
            clearScreen();
            showTitle("Menù Cliente");

            showInfo("1. Login");
            showInfo("2. Registrazione");
            showInfo("3. Esci");

            String choice = askInput("\nSeleziona un'opzione: ");

            switch(choice){
                case "1": return CustomerPages.LOGIN;
                case "2": return CustomerPages.REGISTRATION;
                case "3": return CustomerPages.LOGOUT;
                default : showError("Scelta non valida, riprova.");
            }
        }
    }
}
