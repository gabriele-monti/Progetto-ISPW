package it.foodmood.view.ui.cli;

import it.foodmood.config.UserMode;
import it.foodmood.controller.application.IngredientController;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.CustomerUi;
import it.foodmood.view.ui.ManagerUi;
import it.foodmood.view.ui.cli.customer.CliCustomerMenuView;
import it.foodmood.view.ui.cli.manager.CliIngredientMenuView;
import it.foodmood.view.ui.cli.manager.CliManagerMenuView;

public final class CliFactory implements CustomerUi, ManagerUi{
    
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
    public CustomerPages showHomeCustumerView(boolean logged){
        CliCustomerMenuView view = new CliCustomerMenuView();
        return view.displayPage(logged);
    }

    @Override
    public void showIngredientManagmentView(){
        IngredientController controller = new IngredientController();
        CliIngredientMenuView view = new CliIngredientMenuView(controller);
        view.displayPage();
    }
}