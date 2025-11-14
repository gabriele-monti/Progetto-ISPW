package it.foodmood.view.ui;


public interface LoginView {
    void show();
    void displayError(String message);
    void displaySuccess(String message);
    void onLoginSuccess(); 
} 