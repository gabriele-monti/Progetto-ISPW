package it.foodmood.view.ui.cli;

import it.foodmood.config.UserMode;
import it.foodmood.view.ui.cli.customer.CustomerCliNavigator;
import it.foodmood.view.ui.cli.manager.ManagerCliNavigator;
import it.foodmood.view.ui.cli.waiter.WaiterCliNavigator;

public class CliNavigatorFactory {

    private CliNavigatorFactory() {}

    public static CliNavigator create(UserMode mode, CliFactory factory){
        return switch(mode){
            case CUSTOMER -> new CustomerCliNavigator(factory);
            case WAITER -> new WaiterCliNavigator(factory);
            case MANAGER -> new ManagerCliNavigator(factory);
        };
    }
}
