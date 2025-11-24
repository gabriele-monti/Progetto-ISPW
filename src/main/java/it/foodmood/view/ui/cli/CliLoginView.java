package it.foodmood.view.ui.cli;

import it.foodmood.bean.LoginBean;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.view.boundary.LoginBoundary;

public class CliLoginView extends ConsoleView {

    private static final String TITLE = "LOGIN";

    private final LoginBoundary boundary;

    public CliLoginView(LoginBoundary boundary){
        super();
        this.boundary = boundary;
    }

    @Override
    public CliPages displayPage(){
        while(true){
            clearScreen();
            showTitle(TITLE);
            showInfo("Accedi al tuo account (0 = indietro)\n");

            LoginBean loginBean = new LoginBean();

            while(true){
                String email = askInputOrBack("Email: ");
                try {
                    loginBean.setEmail(email);
                    break;
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            }

            while(true){
                String password = askInputOrBack("Password: ");
                try {
                    loginBean.setPassword(password.toCharArray());
                    break;
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            }

            try {
                boundary.login(loginBean);
                showSuccess("Login effettuato con successo\n");
                waitForEnter(null);
                return CliPages.LOGOUT;
            } catch (AuthenticationException e) {
                clearScreen();
                showError(e.getMessage());
                showInfo("Riprova\n");
            }
        }
    }
}
