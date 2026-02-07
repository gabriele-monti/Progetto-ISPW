package it.foodmood.view.ui.gui.customer;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.CartItemBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.controller.CartController;
import it.foodmood.controller.CustomerOrderController;
import it.foodmood.exception.CartException;
import it.foodmood.exception.OrderException;
import it.foodmood.view.ui.gui.GuiRouter;
import it.foodmood.view.ui.gui.utils.BaseGui;
import it.foodmood.view.ui.gui.utils.ButtonDelete;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class GuiCustomerCart extends BaseGui {
    
    @FXML private Label lblTotalOrder;

    @FXML private TableView<CartItemBean> tblOrder;

    @FXML private TableColumn<CartItemBean, String> colProduct;

    @FXML private TableColumn<CartItemBean, Integer> colQty;

    @FXML private TableColumn<CartItemBean, Void> colDelete;

    @FXML private TableColumn<CartItemBean, String> colPrice;

    @FXML private Button btnAccount;

    @FXML private Button btnCart;

    @FXML private Button btnOrder;

    @FXML private BorderPane cardPane;

    @FXML private Label lblUserInitials;

    private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.ITALY);

    private final CartController cartController = new CartController();

    private TableSessionBean tableSessionBean;
    private ObservableList<CartItemBean> observableItems = FXCollections.observableArrayList();

    private void showOrderRecap(){
        try {
            List<CartItemBean> items = cartController.getCartItems();
            observableItems.setAll(items);
            updateTotal();
        } catch (CartException e) {
            showError(e.getMessage());
            observableItems.clear();
            updateTotal();
        }
    }

    public void setTableSession(TableSessionBean tableSessionBean){
        this.tableSessionBean = tableSessionBean;
    }

    @FXML
    void onAccountClicked(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
        if(!actor.isGuest()){
            router.showCustomerAccountView();
        } else {
            showInfo("Effettua l'accesso per vedere la sezione Account");
        }
    }

    @FXML
    void onBackToHomePage(MouseEvent event) {
        if(!ensureAuthenticated(router)) return;
        router.showHomeCustumerView();
    }

    @FXML
    void onCartClicked(ActionEvent event) {
        showOrderRecap();
    }

    @FXML
    void onOrder(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
       
        try {
            String tableSessionId = tableSessionBean.getTableSessionId().toString();
            
            CustomerOrderController orderController = new CustomerOrderController();
            orderController.createOrder(tableSessionId);

            showInfo("Ordine creato correttamente");

            showOrderRecap();

        } catch (OrderException e){
            showError(e.getMessage());
        }
    }

    @FXML
    private void initialize(){

        tblOrder.setItems(observableItems);

        colProduct.setCellValueFactory(col -> new ReadOnlyObjectWrapper<>(col.getValue().getProductName()));
        colQty.setCellValueFactory(col -> new ReadOnlyObjectWrapper<>(col.getValue().getQuantity()));
        colPrice.setCellValueFactory(col -> new ReadOnlyObjectWrapper<>(currency.format(col.getValue().getSubTotal())));

        colDelete.setCellFactory(tabCell -> new ButtonDelete(line -> {
            removeItem(line.getDishId());
        }));

        showOrderRecap();
    }

    private GuiRouter router;
    private ActorBean actor;

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    public void setUser(ActorBean actor){
        this.actor = actor;
        updateLabel();
    }

    private void updateLabel(){
        if(actor == null) return;

        String initials = getUserInitials(actor);

        if(lblUserInitials != null){
            lblUserInitials.setText(initials);
        }
    }

    private void removeItem(String dishId){
        try {
            cartController.removeFromCart(dishId);
            showOrderRecap();
        } catch (CartException e) {
            showError(e.getMessage());
        }
    }

    private void updateTotal(){
        if(lblTotalOrder == null) return;

        try {
            lblTotalOrder.setText(currency.format(cartController.getTotal()));
        } catch (Exception _) {
            lblTotalOrder.setText(currency.format(0));
        }
    }
}
