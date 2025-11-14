package it.foodmood.view.boundary;

import it.foodmood.bean.AuthenticationBean;
import it.foodmood.bean.LoginBean;
import it.foodmood.config.UserMode;
import it.foodmood.controller.application.LoginController;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.utils.SessionManager;

public class LoginBoundary {
    private final LoginController loginController;

    public LoginBoundary(){
        this.loginController = new LoginController();
    }

    public AuthenticationBean login(LoginBean userBean, UserMode userMode) throws AuthenticationException{
        return loginController.login(userBean, userMode);
    }

    public void logout(){
        SessionManager.getInstance().terminateCurrentSession();
    }
}