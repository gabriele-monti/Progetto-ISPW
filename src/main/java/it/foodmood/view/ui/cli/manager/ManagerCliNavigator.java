package it.foodmood.view.ui.cli.manager;

import it.foodmood.exception.SessionExpiredException;
import it.foodmood.utils.SessionManager;
import it.foodmood.view.ui.ManagerUi;
import it.foodmood.view.ui.cli.CliNavigator;
import it.foodmood.view.ui.cli.ManagerPages;
import it.foodmood.view.ui.cli.ProtectedConsoleView;

public class ManagerCliNavigator extends ProtectedConsoleView implements CliNavigator {

    private final ManagerUi ui;

    public ManagerCliNavigator(ManagerUi managerUi){
        this.ui = managerUi;
    }

    @Override
    public void start(){

        boolean exitApp = false;
        while(!exitApp){
            boolean logged = ui.showLoginView();

            if(!logged){
                exitApp = true;
                continue;
            }

            exitApp = runManagerSession();
        }
    }

    private boolean runManagerSession(){
        SessionManager sessionManager = SessionManager.getInstance();

        while(true){
            try {
                sessionManager.requireActiveSession();

                CliManagerMenuView menuView = new CliManagerMenuView();
                ManagerPages page = menuView.displayPage();

                switch(page){
                    case MANAGMENT_INGREDIENTS -> ui.showIngredientManagmentView();
                    
                    case MANAGMENT_DISH -> ui.showDishManagmentView();

                    case LOGOUT -> {
                        sessionManager.terminateCurrentSession();
                        return false;
                    }

                    case EXIT -> {
                        sessionManager.terminateCurrentSession();
                        return true;
                    }
                } 
            } catch (SessionExpiredException _) {
                showSessionExpiredMessage();
                return false;
            }
        }
    }
}
