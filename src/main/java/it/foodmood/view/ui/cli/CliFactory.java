package it.foodmood.view.ui.cli;

import it.foodmood.config.UserMode;
import it.foodmood.view.boundary.DishBoundary;
import it.foodmood.view.boundary.IngredientBoundary;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.CustomerUi;
import it.foodmood.view.ui.ManagerUi;
import it.foodmood.view.ui.WaiterUi;
import it.foodmood.view.ui.cli.customer.CliCustomerMenuView;
import it.foodmood.view.ui.cli.manager.CliIngredientMenuView;
import it.foodmood.view.ui.cli.manager.CliDishMenuView;
import it.foodmood.view.ui.cli.manager.CliManagerMenuView;
import it.foodmood.view.ui.cli.waiter.CliWaiterMenuView;

public final class CliFactory implements CustomerUi, ManagerUi, WaiterUi{
    
    private final UserMode userMode;

    public CliFactory(UserMode userMode){
        this.userMode = userMode;
    }

    public CliCustomerMenuView createCustomerMenuView(){
        return new CliCustomerMenuView();
    }

    @Override
    public boolean showLoginView(){
        LoginBoundary boundary = new LoginBoundary(userMode);
        CliLoginView view = new CliLoginView(boundary);
        return view.displayPage();
    }  

    @Override
    public void showRegistrationView(){
        RegistrationBoundary boundary = new RegistrationBoundary();
        CliRegistrationView view = new CliRegistrationView(boundary);
        view.displayPage();
    }  

    @Override
    public void showHomeManagerView(){
        CliManagerMenuView view = new CliManagerMenuView();
        view.displayPage();
    }

     @Override
    public void showHomeWaiterView(){
        CliWaiterMenuView view = new CliWaiterMenuView();
        view.displayPage();
    }

    
    @Override
    public CustomerPages showHomeCustumerView(boolean logged){
        CliCustomerMenuView view = new CliCustomerMenuView();
        return view.displayPage(logged);
    }

    @Override
    public void showIngredientManagmentView(){
        IngredientBoundary boundary = new IngredientBoundary();
        CliIngredientMenuView view = new CliIngredientMenuView(boundary);
        view.displayPage();
    }

    @Override
    public void showDishManagmentView(){
        DishBoundary dishBoundary = new DishBoundary();
        IngredientBoundary ingredientBoundary = new IngredientBoundary();
        CliDishMenuView view = new CliDishMenuView(dishBoundary, ingredientBoundary);
        view.displayPage();
    }
}