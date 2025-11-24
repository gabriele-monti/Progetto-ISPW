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
        CliCustomerMenuView menuView = new CliCustomerMenuView();

        boolean exit = false;
        while(!exit){
            CustomerPages page = menuView.displayPage();

            switch (page) {
                case LOGIN ->  ui.showLoginView();
                case REGISTRATION -> ui.showRegistrationView();
                case LOGOUT -> exit = true;
            }
        }
    }
}
