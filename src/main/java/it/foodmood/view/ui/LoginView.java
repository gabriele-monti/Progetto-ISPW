package it.foodmood.view.ui;

import it.foodmood.bean.AuthenticationBean;

public interface LoginView {
    void show();
    void displayError(String message);
    void displaySuccess(String message);
    void onLoginSuccess(AuthenticationBean authentication); 
} 