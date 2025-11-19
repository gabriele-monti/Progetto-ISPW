package it.foodmood.view.ui.cli;

import it.foodmood.bean.LoginBean;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.exception.BackRequestedException;
import it.foodmood.view.boundary.LoginBoundary;

public class CliLoginView extends ConsoleView implements CliView {

    private static final String TITLE = "LOGIN";

    private final LoginBoundary boundary;

    public CliLoginView(LoginBoundary boundary){
        super();
        this.boundary = boundary;
    }

    @Override
    public ViewResult showAndReturn(){
        while(true){
            try{
                showTitle(TITLE);
                showInfo("Inserisci le credenziali (0 per tornare indietro)\n");

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
                    return ViewResult.SUCCESS;
                } catch (AuthenticationException e) {
                    clearScreen();
                    showError(e.getMessage());
                    showInfo("Riprova\n");
                }
            } catch (BackRequestedException _) {
                return ViewResult.BACK;
            }
        }
    }
}
