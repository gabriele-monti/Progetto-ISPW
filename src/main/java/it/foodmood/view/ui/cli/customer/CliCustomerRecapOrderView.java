package it.foodmood.view.ui.cli.customer;

import java.math.BigDecimal;
import java.util.List;

import it.foodmood.bean.OrderBean;
import it.foodmood.bean.OrderLineBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.exception.OrderException;
import it.foodmood.view.boundary.CartBoundary;
import it.foodmood.view.boundary.CustomerOrderBoundary;
import it.foodmood.view.ui.cli.ProtectedConsoleView;

public class CliCustomerRecapOrderView extends ProtectedConsoleView {
    
    private final CartBoundary cartBoundary;
    private final CustomerOrderBoundary orderBoundary;
    
    public CliCustomerRecapOrderView(CartBoundary cartBoundary, CustomerOrderBoundary orderBoundary){
        super();
        this.cartBoundary = cartBoundary;
        this.orderBoundary = orderBoundary;
    }

    public void displayPage(TableSessionBean tableSession){
        clearScreen();
        boolean back = false;
        while(!back){
            showTitle("Il tuo ordine");

            // Da correggere
            if(!showRecapOrder()){
                showWarning("Nessun articolo nell'ordine");
                waitForEnter(null);
                return;
            }

            showInfo("1. Conferma ordine");
            showInfo("2. Rimuovi articolo");
            showInfo("0. Torna al menù principale");

            String choice = askInput("\nSeleziona un'opzione: ");

            switch(choice){
                case "1" -> confirmOrder(tableSession);
                case "2" -> back = true;
                case "0" -> back = true;
                default  -> showError("Scelta non valida, riprova.");
            }
        }
    }

    private void confirmOrder(TableSessionBean tableSessionBean){
        boolean choice = askConfirmation("Vuoi confermare il tuo ordine?");

        if(!choice) return;

        OrderBean orderBean = new OrderBean();

        String tableSessionId = tableSessionBean.getTableSessionId().toString();
        orderBean.setTableSessionId(tableSessionId);

        List<OrderLineBean> orderLines = cartBoundary.getCartItems();
        orderBean.setOrderLines(orderLines);

        try {
            String orderId = orderBoundary.createOrder(orderBean);
            showSuccess("Ordine creato con successo!\nID: " + orderId);
            cartBoundary.clearCart();
            return;
        } catch (OrderException e) {
            showError(e.getMessage());
        }
    }

    private boolean showRecapOrder(){
        List<OrderLineBean> items = cartBoundary.getCartItems();
        if(items.isEmpty()){
            return false;
        }
        BigDecimal total = cartBoundary.getTotal();

        showRecapOrderTable(items);
        showBold("Totale: " + total + "€\n");
        return true;
    }
}
