package it.foodmood.view.ui.gui;

import it.foodmood.domain.model.User;
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
        // commento 
    }

    @FXML
    void onCallWaiterClicked(ActionEvent event) {
        // commento 
    }

    @FXML
    void onCartClicked(ActionEvent event) {
        // commento
    }

    @FXML
    void onMenuClicked(ActionEvent event) {
        // commento 
    }

    @FXML
    void onOrderClicked(ActionEvent event) {
        // commento
    }

    @FXML
    private Label lblUserInitials;

    @FXML
    void onRequestBillClicked(ActionEvent event) {
        // commento
    }

    private GuiFactory factory;

    public void setFactory(GuiFactory factory){
        this.factory = factory;
    }

    public GuiHomeCustomer(){
        // costruttore vuooto
    }

    private User customer;

    public void setUser(User customer){
        this.customer = customer;
        updateLabel();
    }

    private void updateLabel(){
        if(lblUserInitials != null && customer != null){
            lblUserInitials.setText(getUserInitials(customer));
        }
    }

    @FXML
    private void initialize(){
        updateLabel();
    }
}
