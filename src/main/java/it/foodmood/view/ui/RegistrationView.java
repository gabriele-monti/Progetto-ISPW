package it.foodmood.view.ui;

public interface RegistrationView {
    void show();
    void displayError(String message);
    void displaySuccess(String message);
    void onRegistrationSuccess(); 
}