package it.foodmood.ui.core;

public interface UserInterface {
    void showInfo(String message);
    void showError(String message);
    void showSuccess(String message);
    void showWarning(String message);
    void showTitle(String message);
    void showSeparator(String message);
    void waitForEnter(String message);
    void clearScreen();
}
