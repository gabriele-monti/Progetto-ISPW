package it.foodmood.view.ui.cli;

import it.foodmood.bean.LoginBean;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.infrastructure.io.InputReader;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.view.boundary.LoginBoundary;
import it.foodmood.view.ui.LoginView;
import it.foodmood.view.ui.theme.UiTheme;

public class CliLoginView implements LoginView {

    private final String TITLE = "LOGIN";

    private final InputReader in;
    private final OutputWriter out;
    private final UiTheme theme;
    private final LoginBoundary boundary;

    public CliLoginView(InputReader in, OutputWriter out, UiTheme theme, LoginBoundary boundary){
        this.in = in;
        this.out = out;
        this.theme = theme;
        this.boundary = boundary;
    }

    @Override
    public void show(){
        out.displayTitle(TITLE);

        LoginBean loginBean = new LoginBean();

        out.print("Email: ");
        loginBean.setEmail(in.readLine());

        out.print("Password: ");
        String password = in.readLine();
        loginBean.setPassword(password.toCharArray());

        try {
            boundary.login(loginBean);
            onLoginSuccess();
        } catch (AuthenticationException e) {
            displayError(e.getMessage());
        }
    }

    @Override
    public void displayError(String message){
        out.println(theme.error("Errore: " + message));
    }

    @Override
    public void displaySuccess(String message){
        out.println(theme.success(message));
    }

    @Override
    public void onLoginSuccess(){
        displaySuccess("Login effettuato con successo!");
    }
}
