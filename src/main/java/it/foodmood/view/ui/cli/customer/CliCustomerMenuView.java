package it.foodmood.view.ui.cli.customer;

import it.foodmood.view.ui.cli.CustomerPages;
import it.foodmood.view.ui.cli.ConsoleView;

public class CliCustomerMenuView extends ConsoleView {

    public CliCustomerMenuView(){
        super();
    }

    public CustomerPages displayPage(boolean logged){
        clearScreen();
        while(true){
            showTitle("Benvenuto in FoodMood");

            if(!logged){
                showInfo("1. Login");
                showInfo("2. Registrazione");
            } else {
                showInfo("1. Gestisci Account");
                showInfo("2. Fidelity Card");
            }
            showInfo("3. Esci");

            String choice = askInput("\nSeleziona un'opzione: ");

            switch(choice){
                case "1": 
                if(!logged){
                    return CustomerPages.LOGIN;
                } else {
                    showWarning("Funzionalità non ancora implementata.\n");
                    break;
                }
                case "2": 
                if(!logged){
                    return CustomerPages.REGISTRATION;
                } else {
                    showWarning("Funzionalità non ancora implementata.\n");
                    break;
                }
                case "3": return CustomerPages.LOGOUT;
                default : showError("Scelta non valida, riprova.");
            }
        }
    }
}
