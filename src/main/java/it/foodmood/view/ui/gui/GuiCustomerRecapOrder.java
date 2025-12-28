package it.foodmood.view.ui.gui;

import java.util.UUID;

import it.foodmood.domain.model.Order;
import it.foodmood.domain.model.OrderLine;
import it.foodmood.domain.model.User;
import it.foodmood.domain.value.Money;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class GuiCustomerRecapOrder extends BaseGui {
    
    @FXML private Label lblTotalOrder;

    @FXML private TableView<OrderLine> tblOrder;

    @FXML private TableColumn<OrderLine, String> colProduct;

    @FXML private TableColumn<OrderLine, Integer> colQty;

    @FXML private TableColumn<OrderLine, Void> colDelete;

    @FXML private TableColumn<OrderLine, String> colPrice;

    @FXML private Button btnAccount;

    @FXML private Button btnBack;

    @FXML private Button btnCart;

    @FXML private Button btnOrder;

    @FXML private BorderPane cardPane;

    @FXML private Label lblUserInitials;

    @FXML
    void onAccountClicked(ActionEvent event) {
        router.showCustomerAccountView();
    }

    @FXML
    void onBackToHome(ActionEvent event) {
        router.showHomeCustumerView();
    }

    @FXML
    void onBackToHomePage(MouseEvent event) {
        router.showHomeCustumerView();
    }

    @FXML
    void onCartClicked(ActionEvent event) {
        // ritorno la stessa pagina
    }

    @FXML
    private void initialize(){
        colProduct.setCellValueFactory(col -> new ReadOnlyObjectWrapper<>(col.getValue().productName()));
        colQty.setCellValueFactory(col -> new ReadOnlyObjectWrapper<>(col.getValue().quantity()));
        colPrice.setCellValueFactory(col -> new ReadOnlyObjectWrapper<>(col.getValue().unitPrice().toString()));

        tblOrder.setItems(orderLines);

        colDelete.setCellFactory(tabCell -> new TableCell<>(){
            private final Button btn = new Button();
            private final ImageView icon;

            {
                String path = "/icons/trash.png";
                Image img = new Image(getClass().getResourceAsStream(path));
                icon = new ImageView(img);
                icon.setFitHeight(20);
                icon.setFitWidth(20);
                icon.setPreserveRatio(true);

                btn.setGraphic(icon);

                btn.setFocusTraversable(false);
                btn.setTooltip(new Tooltip("Elimina riga"));
                btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                btn.setOnAction(e -> {
                    if(order == null) return;
                    OrderLine line = getTableView().getItems().get(getIndex());

                    order.removeLine(line);

                    orderLines.setAll(order.getOrderLines());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                    setText(null);
                    setStyle("");
                } else {
                    setGraphic(btn);
                    setText(null);
                }
            }
        });

        orderLines.add(esempio);
        orderLines.add(esempio1);
    }

    private final ObservableList<OrderLine> orderLines = FXCollections.observableArrayList();
    private Order order;

    private GuiRouter router;
    private User customer;

    public void setRouter(GuiRouter router){
        this.router = router;
    }

    public void setUser(User customer){
        this.customer = customer;
        updateLabel();
    }

    private void updateLabel(){
        if(customer == null) return;

        String initials = getUserInitials(customer);

        if(lblUserInitials != null){
            lblUserInitials.setText(initials);
        }
    }

    OrderLine esempio = new OrderLine(
        UUID.fromString("11111111-1111-1111-1111-111111111111"),
        "Margherita",
        new Money(7.50),
        2
    );

    OrderLine esempio1 = new OrderLine(
        UUID.fromString("11111111-1111-2222-1111-111111111111"),
        "Carne",
        new Money(23.50),
        1
    );

}
