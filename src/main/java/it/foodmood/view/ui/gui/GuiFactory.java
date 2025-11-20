package it.foodmood.view.ui.gui;

import it.foodmood.config.UserMode;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.UiFactory;
import javafx.scene.Scene;

public final class GuiFactory extends UiFactory{

    private final UserMode userMode;
    private final GuiNavigator navigator;

    public GuiFactory(Scene scene, UserMode userMode){
        this.navigator = new GuiNavigator(scene);
        this.userMode = userMode;
    }

    public void showHomeView(){
        switch (userMode) {
            case CUSTOMER -> showHomeCustumerView();
            case WAITER -> showHomeCustumerView();
            case MANAGER -> showHomeManagerView();
        }
    }
    
    public void showLoginView(){
        GuiLoginView controller = navigator.goTo(GuiPages.LOGIN);
        LoginBoundary boundary = new LoginBoundary(userMode);
        controller.setBoundary(boundary);
        controller.setFactory(this);
    }  

    public void showRegistrationView(){
        GuiRegistrationView controller = navigator.goTo(GuiPages.REGISTRATION);
        RegistrationBoundary boundary = new RegistrationBoundary();
        controller.setBoundary(boundary);
        controller.setFactory(this);
    }

    public void showHomeCustumerView(){
        GuiHomeCustomer controller = navigator.goTo(GuiPages.HOME_CUSTOMER);
        controller.setFactory(this);
    }

    public void showHomeManagerView(){
        GuiHomeManager controller = navigator.goTo(GuiPages.HOME_MANAGER);
        controller.setFactory(this);
    }
}