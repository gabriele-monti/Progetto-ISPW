package it.foodmood.view.ui.cli.customer;

import it.foodmood.bean.ActorBean;
import it.foodmood.view.boundary.GuestAccessBoundary;
import it.foodmood.view.ui.cli.ConsoleView;

public class CliGuestView extends ConsoleView {
    
    private final GuestAccessBoundary boundary;

    public CliGuestView(GuestAccessBoundary boundary){
        super();
        this.boundary = boundary;
    }

    public ActorBean displayPage(){
        clearScreen();
        return boundary.enterAsGuest();
    }
}
