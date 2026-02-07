package it.foodmood.view.ui.gui.customer;

import it.foodmood.bean.ActorBean;
import it.foodmood.view.ui.gui.GuiRouter;
import it.foodmood.view.ui.gui.utils.BaseGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GuiHomeCustomer extends BaseGui {

    @FXML private Button btnAccount;

    @FXML private Button btnCallWaiter;

    @FXML private Button btnCart;

    @FXML private Button btnDigitalMenu;

    @FXML private Label lblTableId;

    @FXML private Button btnOrder;

    @FXML private Button btnRequestBill;
    
    @FXML
    void onAccountClicked(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
        if(!actor.isGuest()){
            router.showCustomerAccountView();
        } else {
            showInfo("Effettua l'accesso per vedere la sezione account");
        }
    }

    @FXML
    void onCallWaiterClicked(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
        showInfo("Funzionalità non ancora implementata");
    }

    @FXML
    void onCartClicked(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
        router.showCustomerRecapOrder();
    }

    @FXML
    void onMenuClicked(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
        router.showCustomerDigitalMenu();
    }

    @FXML
    void onOrderClicked(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
        router.showCustomerOrderView();
    }

    @FXML
    private Label lblUserInitials;

    @FXML
    void onRequestBillClicked(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
        showInfo("Funzionalità non ancora implementata");
    }

    private Integer tableId;

    public void setTableId(int tableId){
        this.tableId = tableId;
        updateLabel();
    }

    private GuiRouter router;

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    public GuiHomeCustomer(){
        // costruttore vuooto
    }

    private ActorBean actor;

    public void setUser(ActorBean actor){
        this.actor = actor;
        updateLabel();
    }

    private void updateLabel(){
        if(lblUserInitials != null && actor != null){
            lblUserInitials.setText(getUserInitials(actor));
        }
        if(lblTableId != null){
            lblTableId.setText(String.valueOf("Tavolo " + tableId));
        }
    }

    @FXML
    private void initialize(){
        updateLabel();
    }
}
