package it.foodmood.view.ui.gui;

import it.foodmood.view.ui.LoginView;
import it.foodmood.view.ui.UiFactory;

public final class GuiFactory extends UiFactory{
    
    @Override
    public LoginView createLoginView(){
        return new GuiLoginView();
    }  
}