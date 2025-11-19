package it.foodmood.view.ui.cli;

import it.foodmood.bean.RegistrationBean;
import it.foodmood.exception.RegistrationException;
import it.foodmood.view.boundary.RegistrationBoundary;
import it.foodmood.view.ui.RegistrationView;

public class CliRegistrationView extends ConsoleView implements RegistrationView{
    
    private static final String TITLE = "REGISTRAZIONE";

    private final RegistrationBoundary boundary;

    public CliRegistrationView(RegistrationBoundary boundary){
        this.boundary = boundary;
    }

    @Override
    public void show(){
        showTitle(TITLE);

        RegistrationBean registrationBean = new RegistrationBean();

        String name = askInput("Nome: ");
        registrationBean.setName(name);

        String surname = askInput("Cognome: ");
        registrationBean.setSurname(surname);

        String email;

        while(true){
            email = askInput("Email: ");
            try {
                registrationBean.setEmail(email);
                break;
            } catch (Exception e) {
                showError(e.getMessage());
            }
        }

        String password;
        String confirmPassword;
        Boolean validPassword = false;

        while(!validPassword){
            password = askInput("Password: ");
            confirmPassword = askInput("Conferma password: ");

            if(!password.equals(confirmPassword)){
                showError("Le password non coincidono. Riprova.");
                continue;
            }

            try {
                registrationBean.setPassword(password.toCharArray());
                registrationBean.setConfirmPassword(confirmPassword.toCharArray());
                validPassword = true;
            } catch (Exception e) {
                showError(e.getMessage());
            }
        }

        try {
            boundary.registration(registrationBean);
            showSuccess("Registrazione effettuata con successo!");
        } catch (RegistrationException e) {
            showError(e.getMessage());
        }
    }
}

