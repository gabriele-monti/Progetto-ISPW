package it.foodmood.view.ui.cli;

import it.foodmood.config.UserMode;
import it.foodmood.view.ui.UiFactory;
import it.foodmood.view.ui.cli.customer.CustomerCliNavigator;

public class CliNavigatorFactory {

    private CliNavigatorFactory() {}

    public static CliNavigator create(UserMode mode, UiFactory uiFactory){
        return switch(mode){
            case CUSTOMER -> new CustomerCliNavigator(uiFactory);
            case WAITER -> new CustomerCliNavigator(uiFactory);
            case MANAGER -> new CustomerCliNavigator(uiFactory);
            // case WAITER -> new WaiterCliNavigator(uiFactory);
            // case MANAGER -> new ManagerCliNavigator(uiFactory);
        };
    }
}
