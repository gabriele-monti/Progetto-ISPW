package it.foodmood.view.ui.gui;

import it.foodmood.domain.model.User;
import it.foodmood.view.boundary.LoginBoundary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GuiHomeManager extends BaseGui {

    @FXML private Button btnHome;

    @FXML private Button btnLogout;

    @FXML private Button btnManagmentBooking;

    @FXML private Button btnManagmentRestaurantRoom;

    @FXML private Button btnManagmentDishes;

    @FXML private Button btnManagmentEmployees;

    @FXML private Button btnManagmentIngredients;

    @FXML private AnchorPane contentArea;

    @FXML private Label lblManager;

    @FXML private AnchorPane mainForm;

    @FXML
    void switchForm(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
        Object source = event.getSource();

        if(source == btnManagmentIngredients){
            GuiManagmentIngredients controller = paneNavigator.show(GuiPages.MANAGMENT_INGREDIENTS);
            controller.setRouter(router);
        } else if(source == btnManagmentDishes){
            GuiManagmentDish controller = paneNavigator.show(GuiPages.MANAGMENT_DISH);
            controller.setRouter(router);
        } else if(source == btnManagmentRestaurantRoom){
            GuiManagmentRestaurantRoom controller = paneNavigator.show(GuiPages.MANAGMENT_ROOM_RESTAURANT);
            controller.setRouter(router);
        } else {
            showInfo("Funzionalità non ancora implementata");
        }
    }

    public GuiHomeManager(){
        // costruttore vuoto
    }

    private GuiRouter router;
    private PaneNavigator paneNavigator;
    private LoginBoundary loginBoundary;
    private User manager;

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    public void setBoundary(LoginBoundary boundary){
        this.loginBoundary = boundary;
    }

    public void setManager(User manager){
        this.manager = manager;
        updateLabel();
    }

    private void updateLabel(){
        if(lblManager != null && manager != null){
            lblManager.setText(getUserFullName(manager));
        }
    }

    @FXML
    void onLogoutClicked(ActionEvent event) {
        loginBoundary.logout();
        router.showLoginView();
    }

    @FXML
    private void initialize(){
        paneNavigator = new PaneNavigator(contentArea);
        updateLabel();

        GuiManagmentDish controller = paneNavigator.show(GuiPages.MANAGMENT_DISH);
        controller.setRouter(router);
    }
}
