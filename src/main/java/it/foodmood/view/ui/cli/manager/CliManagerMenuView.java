package it.foodmood.view.ui.cli.manager;

import it.foodmood.view.ui.cli.ManagerPages;
import it.foodmood.view.ui.cli.ConsoleView;

public class CliManagerMenuView extends ConsoleView {

    public CliManagerMenuView(){
        super();
    }

    public ManagerPages displayPage(){
        clearScreen();
        while(true){
            showTitle("Menù Manager");

            showInfo("1. Gestisci Ingredienti");
            showInfo("2. Logout");
            showInfo("3. Esci");

            String choice = askInput("\nSeleziona un'opzione: ");

            switch(choice){
                case "1": return ManagerPages.MANAGMENT_INGREDIENTS;
                case "2": return ManagerPages.LOGOUT;
                case "3": return ManagerPages.EXIT;
                default : showError("Scelta non valida, riprova.");
            }
        }
    }
}
