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
        showTitle(TITLE);

        LoginBean loginBean = new LoginBean();

        String email = askInput("Email: ");
        loginBean.setEmail(email);

        String password = askInput("Password: ");
        loginBean.setPassword(password.toCharArray());

        try {
            boundary.login(loginBean);
            onLoginSuccess();
        } catch (AuthenticationException e) {
            showError(e.getMessage());
        }
    }

    @Override
    public void onLoginSuccess(){
        showSuccess("Login effettuato con successo!");
    }
}
