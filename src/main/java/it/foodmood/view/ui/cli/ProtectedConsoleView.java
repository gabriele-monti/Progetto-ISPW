package it.foodmood.view.ui.cli;

import java.util.List;

import it.foodmood.bean.IngredientBean;
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

}

