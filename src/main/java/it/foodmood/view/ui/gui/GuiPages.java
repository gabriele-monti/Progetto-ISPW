package it.foodmood.view.ui.gui;

public enum GuiPages {
    LOGIN("/fxml/login.fxml"),
    REGISTRATION("/fxml/registration.fxml"),
    HOME_CUSTOMER("/fxml/home_customer.fxml"),
    HOME_MANAGER("/fxml/home_manager.fxml"),
    MANAGMENT_ROOM_RESTAURANT("/fxml/managment_restaurant_room.fxml"),
    MANAGMENT_DISH("/fxml/managment_dish.fxml"),
    MANAGMENT_INGREDIENTS("/fxml/managment_ingredients.fxml");

    private final String path;

    GuiPages(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
