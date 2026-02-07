package it.foodmood.view.ui.cli;

import it.foodmood.controller.LoginController;

public class CliLogoutView extends ConsoleView {

    private final LoginController loginController;

    public CliLogoutView(){
        super();
        this.loginController = new LoginController();
    }

    public boolean displayPage(){
        boolean choiche = askConfirmation("Vuoi uscire dal tuo account?");
        if(choiche){
            loginController.logout();
            clearScreen();
            showSuccess("Logout completato\n");
            waitForEnter(null);
            return true;
        }
        waitForEnter("Operazione annullata. Premi INVIO per continuare ");
        return false;
    }
}
