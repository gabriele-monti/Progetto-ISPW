package it.foodmood.view.ui.cli;

import it.foodmood.view.ui.LoginView;
import it.foodmood.view.ui.UiFactory;

public final class CliFactory extends UiFactory{
    
    @Override
    public LoginView createLoginView(){
        return new CliLoginView();
    }  
}