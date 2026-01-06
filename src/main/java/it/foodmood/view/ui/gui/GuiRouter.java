package it.foodmood.view.ui.gui;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.TableSessionBean;
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
    private final Cart cart;
    private TableSessionBean currentTableSession;
    private final ActorBean actorBean;

    private void loadActor(){
        var user = SessionManager.getInstance().getCurrentUser();

        if(user == null){
            actorBean.setName(null);
            actorBean.setSurname(null);
            return;
        }

        actorBean.setName(user.getPerson().firstName());
        actorBean.setSurname(user.getPerson().lastName());
    }

    public GuiRouter(Scene scene, UserMode userMode){
        this.navigator = new GuiNavigator(scene);
        this.userMode = userMode;
        this.loginBoundary = new LoginBoundary(userMode);
        this.registrationBoundary = new RegistrationBoundary();
        this.cart = new Cart();
        this.actorBean = new ActorBean();
    }

    public ActorBean getActor(){
        return actorBean;
    }

    public Cart getCart(){
        return cart;
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
        controller.setUser(actorBean);
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
        loadActor();
        GuiHomeCustomer controller = navigator.goTo(GuiPages.HOME_CUSTOMER);
        controller.setRouter(this);
        controller.setUser(actorBean);
        controller.setTableId(currentTableSession.getTableId());
    }

    public void showHomeManagerView(){
        loadActor();
        GuiHomeManager controller = navigator.goTo(GuiPages.HOME_MANAGER);
        controller.setRouter(this);
        controller.setBoundary(loginBoundary);
        controller.setManager(actorBean);
    }

    public void showHomeWaiterView() {
        loadActor();
        GuiHomeWaiter controller = navigator.goTo(GuiPages.HOME_WAITER);
        controller.setRouter(this);
        controller.setBoundary(loginBoundary);
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
        controller.setCart(cart);
        controller.setUser(actorBean);
    }

    public void showCustomerOrderView(){
        GuiCustomerOrder controller = navigator.goTo(GuiPages.CUSTOMER_ORDER);
        controller.setRouter(this);
        controller.setCart(cart);
        controller.setUser(actorBean);
    }

    public void showCustomerRecapOrder(){
        GuiCustomerRecapOrder controller = navigator.goTo(GuiPages.CUSTOMER_RECAP_ORDER);
        controller.setRouter(this);
        controller.setCart(cart);
        controller.setTableSession(currentTableSession);
        controller.setUser(actorBean);
    }

    public void showSessionTableView(){
        GuiTableSession controller = navigator.goTo(GuiPages.TABLE_SESSION);
        controller.setRouter(this);
    }
}
