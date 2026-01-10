package it.foodmood.view.ui.cli;

import java.util.List;

import it.foodmood.bean.DishBean;
import it.foodmood.bean.IngredientBean;
import it.foodmood.bean.OrderLineBean;
import it.foodmood.exception.SessionExpiredException;
import it.foodmood.utils.SessionManager;

public abstract class ProtectedConsoleView extends ConsoleView{
    private final SessionManager sessionManager = SessionManager.getInstance();

    protected boolean ensureActiveSession(){
        try {
            sessionManager.requireActiveSession();
            return true;
        } catch (SessionExpiredException _) {
            showSessionExpiredMessage();
            return false;
        }
    }

    protected void showSessionExpiredMessage(){
        clearScreen();
        showError("La sessione è scaduta. Effettua nuovamente il login.");
        waitForEnter(null);
    }

    protected void showIngredientTable(List<IngredientBean> ingredients){

        if(ingredients == null || ingredients.isEmpty()){
            showWarning("Nessun ingrediente presente.");
            waitForEnter(null);
            return;
        }

        List<String> headers = TableIngredients.ingredientTableHeaders();
        List<List<String>> rows = TableIngredients.ingredientRows(ingredients);
        List<Integer> columnWidths = TableIngredients.ingredientColumnWidths();
        displayTable(headers, rows, columnWidths);

    }

    protected void showDishTable(List<DishBean> dishes){
        if(dishes == null || dishes.isEmpty()){
            showWarning("Nessun piatto presente.");
            waitForEnter(null);
            return;
        }
        List<String> headers = TableDishes.dishTableHeaders();
        List<List<String>> rows = TableDishes.dishRows(dishes);
        List<Integer> columnWidths = TableDishes.dishColumnWidths();
        displayTable(headers, rows, columnWidths);
    }

    protected void showProposeTable(List<DishBean> dishes){
        if(dishes == null || dishes.isEmpty()){
            showWarning("Nessun piatto presente.");
            waitForEnter(null);
            return;
        }
        List<String> headers = TablePropose.dishTableHeaders();
        List<List<String>> rows = TablePropose.dishRows(dishes);
        List<Integer> columnWidths = TablePropose.dishColumnWidths();
        displayTable(headers, rows, columnWidths);
    }

    protected void showRecapOrderTable(List<OrderLineBean> lines){
        if(lines == null || lines.isEmpty()){
            showWarning("Nessun articolo presente.");
            waitForEnter(null);
            return;
        }
        List<String> headers = TableOrder.orderTableHeaders();
        List<List<String>> rows = TableOrder.orderRows(lines);
        List<Integer> columnWidths = TableOrder.orderColumnWidths();
        displayTable(headers, rows, columnWidths);
    }

}

