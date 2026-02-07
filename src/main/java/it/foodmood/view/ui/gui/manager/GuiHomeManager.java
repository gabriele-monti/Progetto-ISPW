package it.foodmood.view.ui.gui.manager;

import it.foodmood.bean.ActorBean;
import it.foodmood.controller.DishController;
import it.foodmood.controller.IngredientController;
import it.foodmood.controller.LoginController;
import it.foodmood.view.ui.gui.GuiPages;
import it.foodmood.view.ui.gui.GuiRouter;
import it.foodmood.view.ui.gui.utils.BaseGui;
import it.foodmood.view.ui.gui.utils.PaneNavigator;
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
            controller.setIngredientController(ingredientController);
        } else if(source == btnManagmentDishes){
            GuiManagmentDish controller = paneNavigator.show(GuiPages.MANAGMENT_DISH);
            controller.setRouter(router);
            controller.setIngredientController(ingredientController);
            controller.setDishController(dishController);
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

    private IngredientController ingredientController = new IngredientController();

    private DishController dishController = new DishController();

    private ActorBean actor;

    public void setRouter(GuiRouter router){
        this.router = router;
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
        LoginController controller = new LoginController(); 
        controller.logout();
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
        controller.setIngredientController(ingredientController);
        controller.setDishController(dishController);
    }
}
