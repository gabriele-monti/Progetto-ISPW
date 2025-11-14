package it.foodmood.view.ui.cli;

import it.foodmood.infrastructure.io.console.ConsoleInputReader;
import it.foodmood.view.ui.LoginView;

public class CliLoginView implements LoginView {

    private final ConsoleInputReader input = ConsoleInputReader.getInstance();

    @Override
    public void show(){
        
    }
}
