package it.foodmood.view.ui.gui;

import javafx.scene.control.Alert;

// import javafx.fxml.FXML;

public abstract class BaseGui {
    

    // @FXML
    // public void logout(){
    //     new LogoutController().logout();
    //     navigator.goToLogin();
    // }

    // protected void changePage(GuiPages page){
    //     navigator.goTo(page);
    // }

    protected void showError(String message){
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }

    protected void showInfo(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
