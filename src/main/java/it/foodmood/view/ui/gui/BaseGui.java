package it.foodmood.view.ui.gui;

import javafx.scene.control.Alert;

// import javafx.fxml.FXML;

public abstract class BaseGui {
    
    protected final GuiNavigator navigator;

    protected BaseGui(GuiNavigator navigator){
        this.navigator = navigator;
    }

    // @FXML
    // public void logout(){
    //     new LogoutController().logout();
    //     navigator.goToLogin();
    // }

    protected void changePage(GuiPages page){
        navigator.goTo(page);
    }

    protected void showError(String message){
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }


}
