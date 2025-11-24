package it.foodmood.view.ui.cli.customer;

import it.foodmood.view.ui.UiFactory;
import it.foodmood.view.ui.cli.CliNavigator;
import it.foodmood.view.ui.cli.CliPages;

public class CustomerCliNavigator implements CliNavigator {

    private final UiFactory factory;

    public CustomerCliNavigator(UiFactory factory){
        this.factory = factory;
    }

    @Override
    public void start(){
        CliCustomerMenuView menuView = new CliCustomerMenuView();

        boolean exit = false;
        while(!exit){
            CliPages page = menuView.show();

            switch (page) {
                case LOGIN ->  factory.showLoginView();
                case REGISTRATION -> factory.showRegistrationView();
                case LOGOUT -> exit = true;
            }
        }
    }
}
