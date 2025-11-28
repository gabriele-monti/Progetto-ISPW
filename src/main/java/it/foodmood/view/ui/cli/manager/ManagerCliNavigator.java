package it.foodmood.view.ui.cli.manager;

import it.foodmood.view.ui.ManagerUi;
import it.foodmood.view.ui.cli.CliNavigator;
import it.foodmood.view.ui.cli.ManagerPages;

public class ManagerCliNavigator implements CliNavigator {

    private final ManagerUi ui;

    public ManagerCliNavigator(ManagerUi managerUi){
        this.ui = managerUi;
    }

    @Override
    public void start(){
        boolean logged = ui.showLoginView();

        if(!logged){
            return;
        }
        
        CliManagerMenuView menuView = new CliManagerMenuView();

        boolean exit = false;

        while(!exit){
            ManagerPages page = menuView.displayPage();

            if(page == ManagerPages.MANAGMENT_INGREDIENTS){
                ui.showIngredientManagmentView();
            } else if(page == ManagerPages.LOGOUT){
                exit = true;
            }
        }
    }
}
