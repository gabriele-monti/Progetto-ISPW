package it.foodmood.view.ui.cli;

import it.foodmood.bean.RegistrationBean;
import it.foodmood.exception.RegistrationException;
import it.foodmood.infrastructure.io.InputReader;
import it.foodmood.infrastructure.io.OutputWriter;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.RegistrationView;
import it.foodmood.view.ui.theme.UiTheme;

public class CliRegistrationView implements RegistrationView{
    private final String TITLE = "REGISTRAZIONE";

    private final InputReader in;
    private final OutputWriter out;
    private final UiTheme theme;
    private final RegistrationBoundary boundary;

    public CliRegistrationView(InputReader in, OutputWriter out, UiTheme theme, RegistrationBoundary boundary){
        this.in = in;
        this.out = out;
        this.theme = theme;
        this.boundary = boundary;
    }

    @Override
    public void show(){
        out.displayTitle(TITLE);

        RegistrationBean registrationBean = new RegistrationBean();

        out.print("Nome: ");
        registrationBean.setName(in.readLine());

        out.print("Cognome: ");
        registrationBean.setSurname(in.readLine());

        out.print("Email: ");
        registrationBean.setEmail(in.readLine());

        out.print("Password: ");
        String password = in.readLine();
        registrationBean.setPassword(password.toCharArray());

        out.print("Conferma password: ");
        String confirmPassword = in.readLine();
        registrationBean.setConfirmPassword(confirmPassword.toCharArray());

        try {
            boundary.registration(registrationBean);
            onRegistrationSuccess();
        } catch (RegistrationException e) {
            e.printStackTrace();
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
    public void onRegistrationSuccess(){
        displaySuccess("Registrazione effettuata con successo!");
    }
}

