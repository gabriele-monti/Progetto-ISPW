package it.foodmood.view.ui.gui;

import it.foodmood.domain.model.User;
import it.foodmood.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GuiHomeCustomer extends BaseGui {

    @FXML
    private Button btnAccount;

    @FXML
    private Button btnCallWaiter;

    @FXML
    private Button btnCart;

    @FXML
    private Button btnDigitalMenu;

    @FXML
    private Button btnOrder;

    @FXML
    private Button btnRequestBill;

    @FXML
    void onAccountClicked(ActionEvent event) {

    }

    @FXML
    void onCallWaiterClicked(ActionEvent event) {

    }

    @FXML
    void onCartClicked(ActionEvent event) {

    }

    @FXML
    void onDigitalMenuClicked(ActionEvent event) {

    }

    @FXML
    void onOrderClicked(ActionEvent event) {

    }

    @FXML
    private Label lblUserInitials;

    @FXML
    void onRequestBillClicked(ActionEvent event) {

    }

    public GuiFactory factory;

    public void setFactory(GuiFactory factory){
        this.factory = factory;
    }

    public GuiHomeCustomer(){
        // costruttore vuooto
    }

    @FXML
    private void initialize(){
        User customer =SessionManager.getInstance().getCurrentUser();

        if(customer != null){
            lblUserInitials.setText(getUserInitials(customer));
        }
    }
}
