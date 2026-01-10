package it.foodmood.view.ui.cli;

import it.foodmood.view.boundary.LoginBoundary;

public class CliLogoutView extends ConsoleView {

    private final LoginBoundary boundary;

    public CliLogoutView(LoginBoundary boundary){
        super();
        this.boundary = boundary;
    }

    public boolean displayPage(){
        boolean choiche = askConfirmation("Vuoi uscire dal tuo account?");
        if(choiche){
            boundary.logout();
            clearScreen();
            showSuccess("Logout completato\n");
            waitForEnter(null);
            return true;
        }
        waitForEnter("Operazione annullata. Premi INVIO per continuare ");
        return false;
    }
}
