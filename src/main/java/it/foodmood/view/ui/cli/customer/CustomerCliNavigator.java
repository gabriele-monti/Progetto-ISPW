package it.foodmood.view.ui.cli.customer;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.view.ui.CustomerUi;
import it.foodmood.view.ui.cli.CliNavigator;
import it.foodmood.view.ui.cli.HomeCustomerPages;
import it.foodmood.view.ui.cli.MenuCustomerPages;

public class CustomerCliNavigator implements CliNavigator {

    private final CustomerUi ui;
    private ActorBean actor;

    public CustomerCliNavigator(CustomerUi customerUi){
        this.ui = customerUi;
    }

    @Override
    public void start(){
        boolean exit = false;

        while(!exit){
            MenuCustomerPages page = ui.showMenuCustumerView();

            switch (page) {
                case LOGIN -> actor = ui.showLoginView();
                case REGISTRATION -> ui.showRegistrationView();
                case GUEST -> actor = ui.showGuestView();
                case EXIT -> { exit = true; continue; }
            }

            boolean backToStartMenu = false;

            TableSessionBean tableSession = ui.showTableSession();

            while(!exit && !backToStartMenu){
                HomeCustomerPages home = ui.showHomeCustumerView(actor, tableSession);
                
                switch (home) {
                    case ORDER_CUSTOMIZATION -> ui.showCustumerOrderCustomizationView();
                    case DIGITAL_MENU -> ui.showDigitalMenuCustumerView();
                    case RECAP_ORDER -> ui.showCustumerRecapOrderView(tableSession);
                    case CALL_WAITER -> ui.showPageNotImplemented();
                    case REQUIRE_BILL -> ui.showPageNotImplemented();
                    case ACCOUNT -> ui.showAccountCustumerView(actor);
                    case LOGOUT -> {
                        if(ui.showLogoutView()){
                            backToStartMenu = true;
                        }
                    }
                    case EXIT -> exit = true;
                }
            }
        }
    }
}
