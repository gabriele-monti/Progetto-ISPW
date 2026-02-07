package it.foodmood.view.ui.cli.navigator;

import it.foodmood.view.ui.cli.ProtectedConsoleView;

public class WaiterCliNavigator extends ProtectedConsoleView implements CliNavigator {

    @Override
    public void start(){
        showWarning("Schermata non implementata in versione CLI");
    }
}
