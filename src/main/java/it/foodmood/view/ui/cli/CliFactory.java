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

    public void showHomeView(){
        switch (userMode) {
            case CUSTOMER -> showHomeCustumerView();
            case WAITER -> showHomeCustumerView();
            case MANAGER -> showHomeManagerView();
        }
    }
    
    @Override
    public void showLoginView(){
        LoginBoundary boundary = new LoginBoundary(userMode);
        CliLoginView view = new CliLoginView(boundary);
        view.setFactory(this);
        view.displayPage();
    }  

    @Override
    public void showRegistrationView(){
        RegistrationBoundary boundary = new RegistrationBoundary();
        CliRegistrationView view = new CliRegistrationView(boundary);
        view.displayPage();
    }  

    public void showHomeManagerView(){
        CliManagerMenuView view = new CliManagerMenuView();
        view.displayPage();
    }

    public void showHomeCustumerView(){
        CliCustomerMenuView view = new CliCustomerMenuView();
        view.displayPage();
    }


    public void showIngredientManagmentView(){
        IngredientController controller = new IngredientController();
        CliIngredientMenuView view = new CliIngredientMenuView(controller);
        view.displayPage();
    }
}