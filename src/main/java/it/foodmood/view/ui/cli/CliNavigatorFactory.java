package it.foodmood.view.ui.cli;

import it.foodmood.config.UserMode;
import it.foodmood.view.ui.cli.customer.CustomerCliNavigator;
import it.foodmood.view.ui.cli.manager.ManagerCliNavigator;

public class CliNavigatorFactory {

    private CliNavigatorFactory() {}

    public static CliNavigator create(UserMode mode, CliFactory factory){
        return switch(mode){
            case CUSTOMER -> new CustomerCliNavigator(factory);
            case WAITER -> new CustomerCliNavigator(factory);
            case MANAGER -> new ManagerCliNavigator(factory);
        };
    }
}
