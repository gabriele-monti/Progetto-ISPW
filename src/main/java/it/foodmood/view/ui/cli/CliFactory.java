package it.foodmood.view.ui.cli;

import it.foodmood.config.UserMode;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.UiFactory;

public final class CliFactory extends UiFactory{
    
    private final UserMode userMode;

    public CliFactory(UserMode userMode){
        this.userMode = userMode;
    }
    
    @Override
    public void showLoginView(){
        LoginBoundary boundary = new LoginBoundary(userMode);
        CliLoginView view = new CliLoginView(boundary);
        view.show();
    }  

    @Override
    public void showRegistrationView(){
        RegistrationBoundary boundary = new RegistrationBoundary();
        CliRegistrationView view = new CliRegistrationView(boundary);
        view.show();
    }  
}