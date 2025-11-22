package it.foodmood.view.ui.gui;

import it.foodmood.domain.model.User;
import javafx.scene.Node;
import javafx.scene.control.Alert;

public abstract class BaseGui {

    protected String getUserInitials(User user){
        if(user == null || user.getPerson() == null) return "";

        String name = user.getPerson().firstName();
        String surname = user.getPerson().lastName();

        String n = (name != null && !name.isEmpty()) ? name.substring(0,1).toUpperCase() : "";
        String s = (surname != null && !surname.isEmpty()) ? surname.substring(0,1).toUpperCase() : "";
        return n + s;
    }

    protected String getUserFullName(User user){
        if(user == null || user.getPerson() == null) return "";

        String name = user.getPerson().firstName();
        String surname = user.getPerson().lastName();

        StringBuilder stringBuilder = new StringBuilder();

        if(name != null && !name.isBlank()){
            stringBuilder.append(name.trim());
        }
        if(surname != null && !surname.isBlank()){
            stringBuilder.append(" ");
            stringBuilder.append(surname.trim());
        } 
        return stringBuilder.toString();
    }

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

    protected void swap(Node nodeShow, Node nodeHide){
        if(nodeShow != null){
            nodeShow.setVisible(true);
            nodeShow.setManaged(true);
        }
        if(nodeHide != null){
            nodeHide.setVisible(false);
            nodeHide.setManaged(false);
        }
    }
}
