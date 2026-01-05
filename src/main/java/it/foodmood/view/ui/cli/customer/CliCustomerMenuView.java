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
            showTitle("Benvenuto al Ristorante il Casale");
            showInfo("Scopri il piacere di ordinare su misura per te\n");

            if(!logged){
                showInfo("1. Login");
                showInfo("2. Registrazione");
                showInfo("3. Esci");
            } else {
                showInfo("1. Ordina con FoodMood");
                showInfo("2. Menù digitale");
                showInfo("3. Chiama un cameriere");
                showInfo("4. Richiedi il conto");
                showInfo("5. Profilo");
                showInfo("6. Logout");
                showInfo("7. Esci");
            }

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
                case "7": return CustomerPages.LOGOUT;
                default : showError("Scelta non valida, riprova.");
            }
        }
    }
}
