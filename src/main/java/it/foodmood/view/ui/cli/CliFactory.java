package it.foodmood.view.ui.cli;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.config.UserMode;
import it.foodmood.view.ui.CustomerUi;
import it.foodmood.view.ui.ManagerUi;
import it.foodmood.view.ui.WaiterUi;
import it.foodmood.view.ui.cli.customer.CliCustomerAccountView;
import it.foodmood.view.ui.cli.customer.CliCustomerDigitalMenuView;
import it.foodmood.view.ui.cli.customer.CliCustomerHomeView;
import it.foodmood.view.ui.cli.customer.CliCustomerMenuView;
import it.foodmood.view.ui.cli.customer.CliCustomerOrderCustomizationView;
import it.foodmood.view.ui.cli.customer.CliCustomerCartView;
import it.foodmood.view.ui.cli.customer.CliGuestView;
import it.foodmood.view.ui.cli.customer.CliTableSessionView;
import it.foodmood.view.ui.cli.manager.CliIngredientMenuView;
import it.foodmood.view.ui.cli.manager.CliDishMenuView;
import it.foodmood.view.ui.cli.manager.CliManagerMenuView;
import it.foodmood.view.ui.cli.pages.HomeCustomerPages;
import it.foodmood.view.ui.cli.pages.MenuCustomerPages;
import it.foodmood.view.ui.cli.waiter.CliWaiterMenuView;

public final class CliFactory implements CustomerUi, ManagerUi, WaiterUi{
    
    private final UserMode userMode;

    public CliFactory(UserMode userMode){
        this.userMode = userMode;
    }

    public CliCustomerHomeView createCustomerMenuView(){
        return new CliCustomerHomeView();
    }

    @Override
    public ActorBean showLoginView(){
        CliLoginView view = new CliLoginView(userMode);
        return view.displayPage();
    } 
    
    @Override
    public boolean showLogoutView(){
        CliLogoutView view = new CliLogoutView();
        return view.displayPage();
    }  

    @Override
    public void showAccountCustumerView(ActorBean actorBean){
        CliCustomerAccountView view = new CliCustomerAccountView();
        view.displayPage(actorBean);
    } 

    @Override
    public TableSessionBean showTableSession(){
        CliTableSessionView view = new CliTableSessionView();
        return view.displayPage();
    }  

    @Override
    public ActorBean showGuestView(){
        CliGuestView view = new CliGuestView();
        return view.displayPage();
    } 

    @Override
    public void showRegistrationView(){
        CliRegistrationView view = new CliRegistrationView();
        view.displayPage();
    }

    @Override
    public void showPageNotImplemented(){
        CliPageNotImplemented view = new CliPageNotImplemented();
        view.displayPage();
    }  

    @Override
    public void showHomeManagerView(ActorBean actorBean){
        CliManagerMenuView view = new CliManagerMenuView();
        view.displayPage(actorBean);
    }

     @Override
    public void showHomeWaiterView(){
        CliWaiterMenuView view = new CliWaiterMenuView();
        view.displayPage();
    }

    @Override
    public MenuCustomerPages showMenuCustumerView(){
        CliCustomerMenuView view = new CliCustomerMenuView();
        return view.displayPage();
    }

    @Override
    public void showDigitalMenuCustumerView(){
        CliCustomerDigitalMenuView view = new CliCustomerDigitalMenuView();
        view.displayPage();
    }

    @Override
    public void showCustumerRecapOrderView(TableSessionBean tableSessionBean){
        CliCustomerCartView view = new CliCustomerCartView();
        view.displayPage(tableSessionBean);
    }
    
    @Override
    public HomeCustomerPages showHomeCustumerView(ActorBean actorBean, TableSessionBean tableSessionBean){
        CliCustomerHomeView view = new CliCustomerHomeView();
        return view.displayPage(actorBean, tableSessionBean);
    }

    @Override
    public void showCustumerOrderCustomizationView(){
        CliCustomerOrderCustomizationView view = new CliCustomerOrderCustomizationView();
        view.displayPage();
    }

    @Override
    public void showIngredientManagmentView(){
        CliIngredientMenuView view = new CliIngredientMenuView();
        view.displayPage();
    }

    @Override
    public void showDishManagmentView(){
        CliDishMenuView view = new CliDishMenuView();
        view.displayPage();
    }
}