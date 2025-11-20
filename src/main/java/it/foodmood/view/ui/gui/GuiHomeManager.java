package it.foodmood.view.ui.gui;

import it.foodmood.domain.model.User;
import it.foodmood.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GuiHomeManager extends BaseGui {

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnManagmentBooking;

    @FXML
    private Button btnManagmentDiningRooms;

    @FXML
    private Button btnManagmentDishes;

    @FXML
    private Button btnManagmentEmployees;

    @FXML
    private Button btnManagmentProduct;

    @FXML
    private AnchorPane contentArea;

    @FXML
    private Label lblManager;

    @FXML
    private AnchorPane main_form;

    @FXML
    void onLogoutClicked(ActionEvent event) {
        factory.showLoginView();
    }

    @FXML
    void switchForm(ActionEvent event) {
        Object source = event.getSource();

        if(source == btnManagmentProduct){
            GuiIngredients controller = paneNavigator.show(GuiPages.INGREDIENTS);
            controller.setFactory(factory);
        }

    }

    public GuiHomeManager(){
        // costruttore vuoto
    }

    public GuiFactory factory;
    private PaneNavigator paneNavigator;

    public void setFactory(GuiFactory factory){
        this.factory = factory;
    }

    @FXML
    private void initialize(){
        paneNavigator = new PaneNavigator(contentArea);

        User manager = SessionManager.getInstance().getCurrentUser();

        if(manager != null){
            lblManager.setText(getUserFullName(manager));
        }

        paneNavigator.show(GuiPages.INGREDIENTS);
    }


}
