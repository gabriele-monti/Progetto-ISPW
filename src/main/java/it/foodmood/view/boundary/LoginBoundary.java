package it.foodmood.view.boundary;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.LoginBean;
import it.foodmood.config.UserMode;
import it.foodmood.controller.application.LoginController;
import it.foodmood.exception.AuthenticationException;

public class LoginBoundary {
    private final LoginController loginController;
    private final UserMode userMode;

    public LoginBoundary(UserMode userMode){
        this.loginController = new LoginController();
        this.userMode = userMode;
    }

    public ActorBean login(LoginBean userBean) throws AuthenticationException{
        return loginController.login(userBean, userMode);
    }

    public void logout(){
        loginController.logout();
    }
}