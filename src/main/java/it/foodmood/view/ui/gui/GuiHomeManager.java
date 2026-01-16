package it.foodmood.view.ui.gui;

import it.foodmood.bean.ActorBean;
import it.foodmood.view.boundary.DishBoundary;
import it.foodmood.view.boundary.IngredientBoundary;
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
            controller.setIngredientBoundary(ingredientBoundary);
        } else if(source == btnManagmentDishes){
            GuiManagmentDish controller = paneNavigator.show(GuiPages.MANAGMENT_DISH);
            controller.setRouter(router);
            controller.setIngredientBoundary(ingredientBoundary);
            controller.setDishBoundary(dishBoundary);
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
    private IngredientBoundary ingredientBoundary;
    private DishBoundary dishBoundary;
    private ActorBean actor;

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    public void setIngredientBoundary(IngredientBoundary ingredientBoundary){
        this.ingredientBoundary = ingredientBoundary;
    }

    public void setDishBoundary(DishBoundary dishBoundary){
        this.dishBoundary = dishBoundary;
    }

    public void setBoundary(LoginBoundary boundary){
        this.loginBoundary = boundary;
    }

    public void setManager(ActorBean actor){
        this.actor = actor;
        updateLabel();
    }

    private void updateLabel(){
        if(lblManager != null && actor != null){
            lblManager.setText(getUserFullName(actor));
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
    }

    public void openDefaultPage(){
        GuiManagmentDish controller = paneNavigator.show(GuiPages.MANAGMENT_DISH);
        controller.setRouter(router);
        controller.setIngredientBoundary(ingredientBoundary);
        controller.setDishBoundary(dishBoundary);
    }
}
