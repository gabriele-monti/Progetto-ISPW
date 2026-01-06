package it.foodmood.view.ui.gui;

import it.foodmood.bean.ActorBean;
import it.foodmood.view.boundary.LoginBoundary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GuiHomeWaiter extends BaseGui {

    @FXML private Button btnBillManagment;

    @FXML private Button btnDishStatus;

    @FXML private Button btnHome;

    @FXML private Button btnLogout;

    @FXML private Button btnManagmentBooking;

    @FXML private Button btnOrderManagment;

    @FXML private AnchorPane contentArea;

    @FXML private Label lblWaiter;

    @FXML private AnchorPane mainForm;

    @FXML
    void switchForm(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
        Object source = event.getSource();

        if(source == btnOrderManagment){
            GuiManagmentOrder controller = paneNavigator.show(GuiPages.MANAGMENT_ORDER);
            controller.setRouter(router);
        } else {
            showInfo("Funzionalità non ancora implementata");
        }
    }

    public GuiHomeWaiter(){
        // costruttore vuoto
    }

    private GuiRouter router;
    private PaneNavigator paneNavigator;
    private LoginBoundary loginBoundary;
    private ActorBean actor;

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    public void setBoundary(LoginBoundary boundary){
        this.loginBoundary = boundary;
    }

    public void setWaiter(ActorBean actor){
        this.actor = actor;
        updateLabel();
    }

    private void updateLabel(){
        if(lblWaiter != null && actor != null){
            lblWaiter.setText(getUserFullName(actor));
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

        GuiManagmentOrder controller = paneNavigator.show(GuiPages.MANAGMENT_ORDER);
        controller.setRouter(router);
    }
}
