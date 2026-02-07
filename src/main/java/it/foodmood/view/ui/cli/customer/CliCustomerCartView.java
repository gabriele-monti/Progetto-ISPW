package it.foodmood.view.ui.cli.customer;

import java.math.BigDecimal;
import java.util.List;

import it.foodmood.bean.CartItemBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.controller.CartController;
import it.foodmood.controller.CustomerOrderController;
import it.foodmood.exception.CartException;
import it.foodmood.exception.OrderException;
import it.foodmood.view.ui.cli.ProtectedConsoleView;

public class CliCustomerCartView extends ProtectedConsoleView {
    
    public CliCustomerCartView(){
        super();
    }

    public void displayPage(TableSessionBean tableSession){
        clearScreen();
        boolean back = false;
        while(!back){
            showTitle("Il tuo ordine");

            if(!showOrderRecap()){
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

        try {
            String tableSessionId = tableSessionBean.getTableSessionId().toString();

            CustomerOrderController orderController = new CustomerOrderController();

            String orderId = orderController.createOrder(tableSessionId);
            
            showSuccess("Ordine creato con successo!\nID: " + orderId);

        } catch (OrderException e) {
            showError(e.getMessage());
        }
    }

    private boolean showOrderRecap(){
        CartController cartController = new CartController();
        try {
            List<CartItemBean> items = cartController.getCartItems();
            if(items.isEmpty()){
                return false;
            }
            BigDecimal total = cartController.getTotal();

            showOrderRecapTable(items);
            showBold("Totale: " + total + "€\n");
            return true;
            
        } catch (CartException e) {
            showWarning(e.getMessage());
            return false;
        }
    }
}
