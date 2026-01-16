package it.foodmood.view.ui.gui;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.config.UserMode;
import it.foodmood.view.boundary.CartBoundary;
import it.foodmood.view.boundary.DishBoundary;
import it.foodmood.view.boundary.IngredientBoundary;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.boundary.RegistrationBoundary;
import javafx.scene.Scene;

public final class GuiRouter{

    private final UserMode userMode;
    private final GuiNavigator navigator;
    private final LoginBoundary loginBoundary;
    private final RegistrationBoundary registrationBoundary;
    private final CartBoundary cartBoundary;
    private final DishBoundary dishBoundary;
    private final IngredientBoundary ingredientBoundary;
    private TableSessionBean currentTableSession;
    
    private ActorBean actorBean;

    public GuiRouter(Scene scene, UserMode userMode){
        this.navigator = new GuiNavigator(scene);
        this.userMode = userMode;
        this.loginBoundary = new LoginBoundary(userMode);
        this.registrationBoundary = new RegistrationBoundary();
        this.cartBoundary = new CartBoundary();
        this.ingredientBoundary = new IngredientBoundary();
        this.dishBoundary = new DishBoundary();
        this.actorBean = new ActorBean();
    }

    public void setActor(ActorBean actorBean){
        this.actorBean = actorBean;
    }

    public ActorBean getActor(){
        return actorBean;
    }

    public void showHomeView(){
        switch (userMode) {
            case CUSTOMER -> showHomeCustumerView();
            case WAITER -> showHomeWaiterView();
            case MANAGER -> showHomeManagerView();
        }
    }

    public void showLoginView(){
        showLoginView(userMode != UserMode.CUSTOMER);
    }  

    public void showLoginView(boolean start){
        GuiLoginView controller = navigator.goTo(GuiPages.LOGIN);
        controller.setBoundary(loginBoundary);
        controller.setRouter(this);
        controller.setUserMode(userMode);
        controller.setStartOnLogin(start);
    } 

    public void showRegistrationView(){
        GuiRegistrationView controller = navigator.goTo(GuiPages.REGISTRATION);
        controller.setBoundary(registrationBoundary);
        controller.setRouter(this);
    }

    public void showHomeCustumerView(TableSessionBean tableSessionBean){
        this.currentTableSession = tableSessionBean;
        showHomeCustumerView();
    }

    public void showHomeCustumerView(){
        GuiHomeCustomer controller = navigator.goTo(GuiPages.HOME_CUSTOMER);
        controller.setRouter(this);
        controller.setUser(actorBean);
        controller.setTableId(currentTableSession.getTableId());
    }

    public void showHomeManagerView(){
        GuiHomeManager controller = navigator.goTo(GuiPages.HOME_MANAGER);
        controller.setRouter(this);
        controller.setBoundary(loginBoundary);
        controller.setIngredientBoundary(ingredientBoundary);
        controller.setDishBoundary(dishBoundary);
        controller.setManager(actorBean);
        controller.openDefaultPage();
    }

    public void showHomeWaiterView() {
        GuiHomeWaiter controller = navigator.goTo(GuiPages.HOME_WAITER);
        controller.setRouter(this);
        controller.setBoundary(loginBoundary);
        controller.setDishBoundary(dishBoundary);
        controller.setWaiter(actorBean);
    }

    public void showCustomerAccountView(){
        GuiCustomerAccount controller = navigator.goTo(GuiPages.CUSTOMER_ACCOUNT);
        controller.setRouter(this);
        controller.setBoundary(loginBoundary);
        controller.setUser(actorBean);
    }

    public void showCustomerDigitalMenu(){
        GuiCustomerDigitalMenu controller = navigator.goTo(GuiPages.CUSTOMER_DIGITAL_MENU);
        controller.setRouter(this);
        controller.setCart(cartBoundary);
        controller.setDishBoundary(dishBoundary);
        controller.setUser(actorBean);
    }

    public void showCustomerOrderView(){
        GuiCustomerOrder controller = navigator.goTo(GuiPages.CUSTOMER_ORDER);
        controller.setRouter(this);
        controller.setCart(cartBoundary);
        controller.setUser(actorBean);
        controller.setDishBoundary(dishBoundary);
    }

    public void showCustomerRecapOrder(){
        GuiCustomerRecapOrder controller = navigator.goTo(GuiPages.CUSTOMER_RECAP_ORDER);
        controller.setRouter(this);
        controller.setCart(cartBoundary);
        controller.setTableSession(currentTableSession);
        controller.setUser(actorBean);
    }

    public void showSessionTableView(){
        GuiTableSession controller = navigator.goTo(GuiPages.TABLE_SESSION);
        controller.setRouter(this);
    }
}
