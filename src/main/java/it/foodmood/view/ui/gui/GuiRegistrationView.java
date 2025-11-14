package it.foodmood.view.ui.gui;

import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.RegistrationView;

public class GuiRegistrationView implements RegistrationView {
    private final RegistrationBoundary boundary;

    public GuiRegistrationView(RegistrationBoundary boundary){
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
    public void onRegistrationSuccess(){
        throw new UnsupportedOperationException("GUI LOGIN SUCCESS");
    }
}
