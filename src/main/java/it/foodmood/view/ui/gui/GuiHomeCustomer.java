package it.foodmood.view.ui.gui;

import it.foodmood.domain.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GuiHomeCustomer extends BaseGui {

    @FXML private Button btnAccount;

    @FXML private Button btnCallWaiter;

    @FXML private Button btnCart;

    @FXML private Button btnDigitalMenu;

    @FXML private Button btnOrder;

    @FXML private Button btnRequestBill;

    @FXML
    void onAccountClicked(ActionEvent event) {
        router.showCustomerAccountView();
    }

    @FXML
    void onCallWaiterClicked(ActionEvent event) {
        showInfo("Un cameriere è stato avvistato, arriverà a breve");
    }

    @FXML
    void onCartClicked(ActionEvent event) {
        router.showCustomerRecapOrder();
    }

    @FXML
    void onMenuClicked(ActionEvent event) {
        router.showCustomerDigitalMenu();
    }

    @FXML
    void onOrderClicked(ActionEvent event) {
        router.showCustomerOrderView();
    }

    @FXML
    private Label lblUserInitials;

    @FXML
    void onRequestBillClicked(ActionEvent event) {
        showInfo("Il conto è stato richiesto");
    }

    private GuiRouter router;

    public void setRouter(GuiRouter router){
        this.router = router;
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
