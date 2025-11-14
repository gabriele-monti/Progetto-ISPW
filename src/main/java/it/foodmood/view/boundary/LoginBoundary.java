package it.foodmood.view.boundary;

import it.foodmood.bean.LoginBean;
import it.foodmood.config.UserMode;
import it.foodmood.controller.application.LoginController;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.utils.SessionManager;

public class LoginBoundary {
    private final LoginController loginController;
    private final UserMode userMode;

    public LoginBoundary(UserMode userMode){
        this.loginController = new LoginController();
        this.userMode = userMode;
    }

    public void login(LoginBean userBean) throws AuthenticationException{
        loginController.login(userBean, userMode);
    }

    public void logout(){
        SessionManager.getInstance().terminateCurrentSession();
    }
}