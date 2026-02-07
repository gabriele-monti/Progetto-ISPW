package it.foodmood.view.ui.cli.customer;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.exception.SessionExpiredException;
import it.foodmood.view.ui.CustomerUi;
import it.foodmood.view.ui.cli.ProtectedConsoleView;
import it.foodmood.view.ui.cli.navigator.CliNavigator;
import it.foodmood.view.ui.cli.pages.HomeCustomerPages;
import it.foodmood.view.ui.cli.pages.MenuCustomerPages;

public class CustomerCliNavigator extends ProtectedConsoleView implements CliNavigator {

    private final CustomerUi ui;
    private ActorBean actor;

    public CustomerCliNavigator(CustomerUi customerUi){
        this.ui = customerUi;
    }

    @Override
    public void start(){
        boolean exit = false;

        while(!exit){
            exit = menuNavigation();
        }
    }

    private boolean menuNavigation(){
        MenuCustomerPages page = ui.showMenuCustumerView();

        switch (page) {
            case LOGIN -> actor = ui.showLoginView();
            case REGISTRATION -> {
                ui.showRegistrationView();
                return false;
            }
            case GUEST -> actor = ui.showGuestView();
            case EXIT -> { return true; }
        }

        return homeNavigation();
    }

    private boolean homeNavigation(){

        boolean exit = false;

        TableSessionBean tableSession = ui.showTableSession();

        while(!exit){
            try {
                HomeCustomerPages home = ui.showHomeCustumerView(actor, tableSession);
                
                ensureActiveSession();
                
                switch (home) {
                    case ORDER_CUSTOMIZATION -> ui.showCustumerOrderCustomizationView();
                    case DIGITAL_MENU -> ui.showDigitalMenuCustumerView();
                    case RECAP_ORDER -> ui.showCustumerRecapOrderView(tableSession);
                    case CALL_WAITER -> ui.showPageNotImplemented();
                    case REQUIRE_BILL -> ui.showPageNotImplemented();
                    case ACCOUNT -> ui.showAccountCustumerView(actor);
                    case LOGOUT -> exit = ui.showLogoutView();
                    case EXIT -> exit = true;
                }
            } catch (SessionExpiredException e) {
                showExceptionMessage(e.getMessage());
                return false;
            }
        }
        return false;
    }
}
