package it.foodmood.view.ui.gui;

import it.foodmood.config.UserMode;
import it.foodmood.view.ui.LoginView;
import it.foodmood.view.ui.RegistrationView;
import it.foodmood.view.ui.UiFactory;

public final class GuiFactory extends UiFactory{

    private final UserMode userMode;

    public GuiFactory(UserMode userMode){
        this.userMode = userMode;
    }
    
    @Override
    public LoginView createLoginView(){
        throw new UnsupportedOperationException("GUI non ancora implementata");
    }  

    @Override
    public RegistrationView createRegistrationView(){
        throw new UnsupportedOperationException("GUI non ancora implementata");
    }  
}