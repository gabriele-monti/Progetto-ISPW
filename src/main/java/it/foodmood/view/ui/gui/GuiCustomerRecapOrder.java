package it.foodmood.view.ui.gui;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.OrderBean;
import it.foodmood.bean.OrderLineBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.exception.OrderException;
import it.foodmood.view.boundary.CustomerOrderBoundary;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class GuiCustomerRecapOrder extends BaseGui {
    
    @FXML private Label lblTotalOrder;

    @FXML private TableView<OrderLineBean> tblOrder;

    @FXML private TableColumn<OrderLineBean, String> colProduct;

    @FXML private TableColumn<OrderLineBean, Integer> colQty;

    @FXML private TableColumn<OrderLineBean, Void> colDelete;

    @FXML private TableColumn<OrderLineBean, String> colPrice;

    @FXML private Button btnAccount;

    @FXML private Button btnCart;

    @FXML private Button btnOrder;

    @FXML private BorderPane cardPane;

    @FXML private Label lblUserInitials;

    private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.ITALY);

    private final CustomerOrderBoundary orderBoundary = new CustomerOrderBoundary();

    private Cart cart;

    private TableSessionBean tableSessionBean;

    public void setCart(Cart cart){
        this.cart = cart;
        tblOrder.setItems(cart.getItems());

        cart.getItems().addListener((ListChangeListener<OrderLineBean>) c -> updateTotal());

        updateTotal();
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
        // ritorno la stessa pagina
    }

    @FXML
    void onOrder(ActionEvent event) {
        if(!ensureAuthenticated(router)) return;
       
        OrderBean orderBean = new OrderBean();
        try {
            String tableSessionId = tableSessionBean.getTableSessionId().toString();
            orderBean.setTableSessionId(tableSessionId);
            List<OrderLineBean> orderLines = List.copyOf(cart.getItems());
            orderBean.setOrderLines(orderLines);

            String orderId = orderBoundary.createOrder(orderBean);

            showInfo("Ordine creato correttamente: " + orderId);
            cart.clear();
        } catch (OrderException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void initialize(){
        colProduct.setCellValueFactory(col -> new ReadOnlyObjectWrapper<>(col.getValue().getProductName()));
        colQty.setCellValueFactory(col -> new ReadOnlyObjectWrapper<>(col.getValue().getQuantity()));
        colPrice.setCellValueFactory(col -> new ReadOnlyObjectWrapper<>(currency.format(col.getValue().getSubTotal())));

        colDelete.setCellFactory(tabCell -> new ButtonDelete(line -> {
            if(cart == null) return;
            cart.removeItem(line.getDishId());
            updateTotal();
        }));
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

    private void updateTotal(){
        if(lblTotalOrder == null) return;

        if(cart == null){
            lblTotalOrder.setText(currency.format(0));
            return;
        }
        lblTotalOrder.setText(currency.format(cart.total()));
    }
}
