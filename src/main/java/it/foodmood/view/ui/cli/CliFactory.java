package it.foodmood.view.ui.cli;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.config.UserMode;
import it.foodmood.view.boundary.CartBoundary;
import it.foodmood.view.boundary.CustomerOrderBoundary;
import it.foodmood.view.boundary.CustomerOrderCustomizationBoundary;
import it.foodmood.view.boundary.DishBoundary;
import it.foodmood.view.boundary.GuestAccessBoundary;
import it.foodmood.view.boundary.IngredientBoundary;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.boundary.MenuBoundary;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.boundary.TableSessionBoundary;
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
import it.foodmood.view.ui.cli.waiter.CliWaiterMenuView;

public final class CliFactory implements CustomerUi, ManagerUi, WaiterUi{
    
    private final LoginBoundary loginBoundary;
    private final GuestAccessBoundary guestAccessBoundary;
    private final TableSessionBoundary tableSessionBoundary;
    private final RegistrationBoundary registrationBoundary;
    private final IngredientBoundary ingredientBoundary;
    private final DishBoundary dishBoundary;
    private final MenuBoundary menuBoundary;
    private final CartBoundary cartBoundary;
    private final CustomerOrderCustomizationBoundary orderCustomizationBoundary;
    private final CustomerOrderBoundary customerOrderBoundary;

    public CliFactory(UserMode userMode){
        this.loginBoundary = new LoginBoundary(userMode);
        this.guestAccessBoundary = new GuestAccessBoundary();
        this.tableSessionBoundary = new TableSessionBoundary();
        this.registrationBoundary = new RegistrationBoundary();
        this.ingredientBoundary = new IngredientBoundary();
        this.dishBoundary = new DishBoundary();
        this.menuBoundary = new MenuBoundary();
        this.cartBoundary = new CartBoundary();
        this.orderCustomizationBoundary = new CustomerOrderCustomizationBoundary();
        this.customerOrderBoundary = new CustomerOrderBoundary();
    }

    public CliCustomerHomeView createCustomerMenuView(){
        return new CliCustomerHomeView();
    }

    @Override
    public ActorBean showLoginView(){
        CliLoginView view = new CliLoginView(loginBoundary);
        return view.displayPage();
    } 
    
    @Override
    public boolean showLogoutView(){
        CliLogoutView view = new CliLogoutView(loginBoundary);
        return view.displayPage();
    }  

    @Override
    public void showAccountCustumerView(ActorBean actorBean){
        CliCustomerAccountView view = new CliCustomerAccountView();
        view.displayPage(actorBean);
    } 

    @Override
    public TableSessionBean showTableSession(){
        CliTableSessionView view = new CliTableSessionView(tableSessionBoundary);
        return view.displayPage();
    }  

    @Override
    public ActorBean showGuestView(){
        CliGuestView view = new CliGuestView(guestAccessBoundary);
        return view.displayPage();
    } 

    @Override
    public void showRegistrationView(){
        CliRegistrationView view = new CliRegistrationView(registrationBoundary);
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
        CliCustomerDigitalMenuView view = new CliCustomerDigitalMenuView(menuBoundary, cartBoundary);
        view.displayPage();
    }

    @Override
    public void showCustumerRecapOrderView(TableSessionBean tableSessionBean){
        CliCustomerCartView view = new CliCustomerCartView(cartBoundary, customerOrderBoundary);
        view.displayPage(tableSessionBean);
    }
    
    @Override
    public HomeCustomerPages showHomeCustumerView(ActorBean actorBean, TableSessionBean tableSessionBean){
        CliCustomerHomeView view = new CliCustomerHomeView();
        return view.displayPage(actorBean, tableSessionBean);
    }

    @Override
    public void showCustumerOrderCustomizationView(){
        CliCustomerOrderCustomizationView view = new CliCustomerOrderCustomizationView(orderCustomizationBoundary, cartBoundary);
        view.displayPage();
    }

    @Override
    public void showIngredientManagmentView(){
        CliIngredientMenuView view = new CliIngredientMenuView(ingredientBoundary);
        view.displayPage();
    }

    @Override
    public void showDishManagmentView(){
        CliDishMenuView view = new CliDishMenuView(dishBoundary, ingredientBoundary);
        view.displayPage();
    }
}