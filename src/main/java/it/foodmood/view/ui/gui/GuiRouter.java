package it.foodmood.view.ui.gui;

import it.foodmood.config.UserMode;
import it.foodmood.utils.SessionManager;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.boundary.RegistrationBoundary;
import javafx.scene.Scene;

public final class GuiRouter{

    private final UserMode userMode;
    private final GuiNavigator navigator;
    private final LoginBoundary loginBoundary;
    private final RegistrationBoundary registrationBoundary;

    public GuiRouter(Scene scene, UserMode userMode){
        this.navigator = new GuiNavigator(scene);
        this.userMode = userMode;
        this.loginBoundary = new LoginBoundary(userMode);
        this.registrationBoundary = new RegistrationBoundary();
    }

    public void showHomeView(){
        switch (userMode) {
            case CUSTOMER -> showHomeCustumerView();
            case WAITER -> showHomeWaiterView();
            case MANAGER -> showHomeManagerView();
        }
    }
    

    public void showLoginView(){
        GuiLoginView controller = navigator.goTo(GuiPages.LOGIN);
        controller.setBoundary(loginBoundary);
        controller.setRouter(this);
    }  

    public void showRegistrationView(){
        GuiRegistrationView controller = navigator.goTo(GuiPages.REGISTRATION);
        controller.setBoundary(registrationBoundary);
        controller.setRouter(this);
    }

    public void showHomeCustumerView(){
        GuiHomeCustomer controller = navigator.goTo(GuiPages.HOME_CUSTOMER);
        controller.setRouter(this);
        controller.setUser(SessionManager.getInstance().getCurrentUser());
    }

    public void showHomeManagerView(){
        GuiHomeManager controller = navigator.goTo(GuiPages.HOME_MANAGER);
        controller.setRouter(this);
        controller.setBoundary(loginBoundary);
        controller.setManager(SessionManager.getInstance().getCurrentUser());
    }

    public void showHomeWaiterView() {
        GuiHomeWaiter controller = navigator.goTo(GuiPages.HOME_WAITER);
        controller.setRouter(this);
        controller.setBoundary(loginBoundary);
        controller.setWaiter(SessionManager.getInstance().getCurrentUser());
    }

    public void showCustomerAccountView(){
        GuiCustomerAccount controller = navigator.goTo(GuiPages.CUSTOMER_ACCOUNT);
        controller.setRouter(this);
        controller.setBoundary(loginBoundary);
        controller.setUser(SessionManager.getInstance().getCurrentUser());
    }

    public void showCustomerDigitalMenu(){
        GuiCustomerMenu controller = navigator.goTo(GuiPages.CUSTOMER_DIGITAL_MENU);
        controller.setRouter(this);
        controller.setUser(SessionManager.getInstance().getCurrentUser());
    }

    public void showCustomerOrderView(){
        GuiCustomerOrder controller = navigator.goTo(GuiPages.CUSTOMER_ORDER);
        controller.setRouter(this);
        controller.setUser(SessionManager.getInstance().getCurrentUser());
    }

    public void showCustomerRecapOrder(){
        GuiCustomerRecapOrder controller = navigator.goTo(GuiPages.CUSTOMER_RECAP_ORDER);
        controller.setRouter(this);
        controller.setUser(SessionManager.getInstance().getCurrentUser());
    }
}
