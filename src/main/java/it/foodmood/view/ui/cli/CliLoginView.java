package it.foodmood.view.ui.cli;

import it.foodmood.bean.LoginBean;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.infrastructure.io.InputReader;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.ui.LoginView;
import it.foodmood.view.ui.theme.UiTheme;

public class CliLoginView extends ConsoleView implements LoginView {

    private static final String TITLE = "LOGIN";

    private final LoginBoundary boundary;

    public CliLoginView(InputReader in, OutputWriter out, UiTheme theme, LoginBoundary boundary){
        super(in, out, theme);
        this.boundary = boundary;
    }

    @Override
    public void show(){
        while(true){
            showTitle(TITLE);

            LoginBean loginBean = new LoginBean();

            String email;
            String password;

            while(true){
                email = askInput("Email: ");
                try {
                    loginBean.setEmail(email);
                    break;
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            }

            while(true){
                password = askInput("Password: ");
                try {
                    loginBean.setPassword(password.toCharArray());
                    break;
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            }

            try {
                boundary.login(loginBean);
                onLoginSuccess();
                break;
            } catch (AuthenticationException e) {
                clearScreen();
                showError(e.getMessage());
                showInfo("Riprova\n");
            }
        }
    }

    @Override
    public void onLoginSuccess(){
        showSuccess("Login effettuato con successo!");
    }
}
