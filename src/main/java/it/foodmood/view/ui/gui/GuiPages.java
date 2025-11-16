package it.foodmood.view.ui.gui;

public enum GuiPages {
    LOGIN("/fxml/login.fxml");

    private final String path;

    GuiPages(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
