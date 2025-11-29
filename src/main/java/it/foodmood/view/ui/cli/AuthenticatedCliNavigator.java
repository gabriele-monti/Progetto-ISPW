package it.foodmood.view.ui.cli;

import it.foodmood.exception.SessionExpiredException;
import it.foodmood.utils.SessionManager;

public interface AuthenticatedCliNavigator extends CliNavigator {
    default void ensureSessionActive(){
        if(!SessionManager.getInstance().isUserLoggedIn()){
            throw new SessionExpiredException();
        }
    }
}
