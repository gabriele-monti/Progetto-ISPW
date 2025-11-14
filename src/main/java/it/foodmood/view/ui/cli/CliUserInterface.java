package it.foodmood.view.ui.cli;

import it.foodmood.view.ui.core.UserInterface;

public interface CliUserInterface extends UserInterface {
    void showTitle(String message);
    void showSeparator(String message); 
    void waitForEnter(String message); 
    void clearScreen();
}
