package it.foodmood.view.ui.cli;

import it.foodmood.bean.ActorBean;
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

    public ActorBean displayPage(){
        clearScreen();
        while(true){
            showTitle(TITLE);
            showInfo("Accedi al tuo account\n");
            LoginBean loginBean = new LoginBean();
            while(true){
                String email = askInput("Email: ");
                try {
                    loginBean.setEmail(email);
                    break;
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            }
            while(true){
                String password = askInput("Password: ");
                try {
                    loginBean.setPassword(password.toCharArray());
                    break;
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            }
            try {
                ActorBean actor = boundary.login(loginBean);
                showSuccess("Login effettuato con successo");
                waitForEnter(null);
                return actor;
            } catch (AuthenticationException e) {
                clearScreen();
                showError(e.getMessage());
                showInfo("Riprova\n");
            }
        }
    }
}
