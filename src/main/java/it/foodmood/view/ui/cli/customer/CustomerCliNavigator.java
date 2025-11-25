package it.foodmood.view.ui.cli.customer;

import it.foodmood.view.ui.CustomerUi;
import it.foodmood.view.ui.cli.CliNavigator;
import it.foodmood.view.ui.cli.CustomerPages;

public class CustomerCliNavigator implements CliNavigator {

    private final CustomerUi ui;

    public CustomerCliNavigator(CustomerUi customerUi){
        this.ui = customerUi;
    }

    @Override
    public void start(){

        boolean logged = false;
        boolean exit = false;

        while(!exit){
            CustomerPages page = ui.showHomeCustumerView(logged);

            switch (page) {
                case LOGIN -> logged = ui.showLoginView();
                case REGISTRATION -> ui.showRegistrationView();
                case LOGOUT -> exit = true;
            }
        }
    }
}
