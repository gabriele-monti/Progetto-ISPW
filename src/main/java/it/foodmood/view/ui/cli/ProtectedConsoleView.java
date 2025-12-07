package it.foodmood.view.ui.cli;

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
}

