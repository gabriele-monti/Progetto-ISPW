package it.foodmood.view.ui.cli.manager;

import it.foodmood.bean.ActorBean;
import it.foodmood.exception.SessionExpiredException;
import it.foodmood.view.ui.ManagerUi;
import it.foodmood.view.ui.cli.TableConsoleView;
import it.foodmood.view.ui.cli.navigator.CliNavigator;
import it.foodmood.view.ui.cli.pages.ManagerPages;

public class ManagerCliNavigator extends TableConsoleView implements CliNavigator {

    private final ManagerUi ui;
    ActorBean actor;

    public ManagerCliNavigator(ManagerUi managerUi){
        this.ui = managerUi;
        this.actor = new ActorBean();
    }

    @Override
    public void start(){

        boolean exitApp = false;
        while(!exitApp){
            actor = ui.showLoginView();

            if(!actor.isLogged()){
                exitApp = true;
                continue;
            }

            exitApp = runManagerSession();
        }
    }

    private boolean runManagerSession(){

        while(true){
            try {
                CliManagerMenuView menuView = new CliManagerMenuView();
                ManagerPages page = menuView.displayPage(actor);

                switch(page){
                    case MANAGMENT_INGREDIENTS -> ui.showIngredientManagmentView();
                    case MANAGMENT_DISH -> ui.showDishManagmentView();
                    case LOGOUT -> { return !ui.showLogoutView(); }
                    case EXIT -> { return true; }
                }

            } catch (SessionExpiredException e) {
                showExceptionMessage(e.getMessage());
                return false;
            }
        }
    }
}
