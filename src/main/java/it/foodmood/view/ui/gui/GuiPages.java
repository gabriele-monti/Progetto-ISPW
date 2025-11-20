package it.foodmood.view.ui.gui;

public enum GuiPages {
    LOGIN("/fxml/login.fxml"),
    REGISTRATION("/fxml/registration.fxml"),
    HOME_CUSTOMER("/fxml/home_customer.fxml"),
    HOME_MANAGER("/fxml/home_manager.fxml"),
    INGREDIENTS("/fxml/ingredients.fxml");

    private final String path;

    GuiPages(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
