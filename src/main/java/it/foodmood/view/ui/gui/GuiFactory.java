package it.foodmood.view.ui.gui;

import it.foodmood.config.UserMode;
import it.foodmood.utils.SessionManager;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.BaseUi;
import javafx.scene.Scene;

public final class GuiFactory implements BaseUi{

    private final UserMode userMode;
    private final GuiNavigator navigator;
    private final LoginBoundary loginBoundary;
    private final RegistrationBoundary registrationBoundary;

    public GuiFactory(Scene scene, UserMode userMode){
        this.navigator = new GuiNavigator(scene);
        this.userMode = userMode;
        this.loginBoundary = new LoginBoundary(userMode);
        this.registrationBoundary = new RegistrationBoundary();
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
        controller.setBoundary(loginBoundary);
        controller.setFactory(this);
    }  

    public void showRegistrationView(){
        GuiRegistrationView controller = navigator.goTo(GuiPages.REGISTRATION);
        controller.setBoundary(registrationBoundary);
        controller.setFactory(this);
    }

    public void showHomeCustumerView(){
        GuiHomeCustomer controller = navigator.goTo(GuiPages.HOME_CUSTOMER);
        controller.setFactory(this);
        controller.setUser(SessionManager.getInstance().getCurrentUser());
    }

    public void showHomeManagerView(){
        GuiHomeManager controller = navigator.goTo(GuiPages.HOME_MANAGER);
        controller.setFactory(this);
        controller.setBoundary(loginBoundary);
        controller.setManager(SessionManager.getInstance().getCurrentUser());
    }
}