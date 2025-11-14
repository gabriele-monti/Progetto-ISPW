package it.foodmood.view.ui.gui;

import it.foodmood.config.UserMode;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.ui.LoginView;
import it.foodmood.view.ui.UiFactory;

public final class GuiFactory extends UiFactory{

    private final UserMode userMode;

    public GuiFactory(UserMode userMode){
        this.userMode = userMode;
    }
    
    @Override
    public LoginView createLoginView(){
        LoginBoundary boundary = new LoginBoundary(userMode);
        return new GuiLoginView(boundary);
    }  
}