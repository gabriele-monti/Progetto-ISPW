package it.foodmood.view.ui.gui;

import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.ui.LoginView;

public class GuiLoginView implements LoginView {
    private final LoginBoundary boundary;

    public GuiLoginView(LoginBoundary boundary){
        this.boundary = boundary;
    }

    @Override
    public void show(){
        throw new UnsupportedOperationException("GUI non ancora implementata");
    }

    @Override
    public void displayError(String message){
        System.err.println("GUI ERROR: " + message);
    }

    @Override
    public void displaySuccess(String message){
        throw new UnsupportedOperationException("GUI non ancora implementata");
    }


    @Override
    public void onLoginSuccess(){
        throw new UnsupportedOperationException("GUI LOGIN SUCCESS");
    }



}
