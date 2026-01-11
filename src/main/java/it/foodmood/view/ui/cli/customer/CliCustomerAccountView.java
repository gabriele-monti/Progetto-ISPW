package it.foodmood.view.ui.cli.customer;

import it.foodmood.bean.ActorBean;
import it.foodmood.view.ui.cli.ProtectedConsoleView;

public class CliCustomerAccountView extends ProtectedConsoleView {

    public CliCustomerAccountView(){
        super();
    }

    public void displayPage(ActorBean actorBean){
        boolean logged = actorBean.isLogged();
        if(!logged){
            showWarning("Effettua l'accesso per vedere la sezione Account");
            waitForEnter(null);
            clearScreen();
        }

        showWarning("Funzionalità non ancora implementata.");
        waitForEnter(null);
    }
}
