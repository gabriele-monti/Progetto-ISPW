package it.foodmood.view.ui.gui;

import it.foodmood.config.UserMode;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.LoginView;
import it.foodmood.view.ui.RegistrationView;
import it.foodmood.view.ui.UiFactory;
import javafx.stage.Stage;

public final class GuiFactory extends UiFactory{

    private final UserMode userMode;
    private final GuiNavigator navigator;

    public GuiFactory(Stage stage, UserMode userMode){
        this.navigator = new GuiNavigator(stage);
        this.userMode = userMode;
    }
    
    @Override
    public LoginView createLoginView(){
        GuiLoginView controller = navigator.goTo(GuiPages.LOGIN);
        LoginBoundary boundary = new LoginBoundary(userMode);
        controller.setBoundary(boundary);
        controller.setNavigator(navigator);
        return controller;
    }  

    @Override
    public RegistrationView createRegistrationView(){
        GuiRegistrationView controller = navigator.goTo(GuiPages.REGISTRATION);
        RegistrationBoundary boundary = new RegistrationBoundary();
        controller.setBoundary(boundary);
        controller.setNavigator(navigator);
        return controller;
    }  
}