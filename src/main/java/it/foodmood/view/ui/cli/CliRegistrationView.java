package it.foodmood.view.ui.cli;

import it.foodmood.bean.RegistrationBean;
import it.foodmood.exception.BackRequestedException;
import it.foodmood.exception.RegistrationException;
import it.foodmood.view.boundary.RegistrationBoundary;

public class CliRegistrationView extends ConsoleView {
    
    private static final String TITLE = "REGISTRAZIONE";

    private final RegistrationBoundary boundary;

    public CliRegistrationView(RegistrationBoundary boundary){
        this.boundary = boundary;
    }

    public void displayPage(){
        try {
            clearScreen();
            while(true){
                showTitle(TITLE);
                showInfo("Crea il tuo account");

                RegistrationBean registrationBean = build();

                if(submitRegistration(registrationBean)){
                    return;
                }
            }
        } catch (BackRequestedException _) {
            // comment
        }
    }

    private boolean submitRegistration(RegistrationBean registrationBean) {
        try {
            boundary.registration(registrationBean);
            showSuccess("Registrazione effettuata con successo!");
            waitForEnter(null);
            return true;
        } catch (RegistrationException e) {
            showError(e.getMessage());
            waitForEnter(null);
            return false;
        } catch (BackRequestedException e){
            return true;
        }
    }

    private RegistrationBean build() throws BackRequestedException {
        RegistrationBean registrationBean = new RegistrationBean();

        String name = askInputOrBack("Nome");
        registrationBean.setName(name);

        String surname = askInputOrBack("Cognome");
        registrationBean.setSurname(surname);

        askAndSetEmail(registrationBean);
        askAndSetPassword(registrationBean);

        return registrationBean;
    }

    private void askAndSetPassword(RegistrationBean registrationBean) {
        while(true){
            String password = askInputOrBack("Password");
            String confirmPassword = askInputOrBack("Conferma password");

            try {
                registrationBean.setPassword(password.toCharArray());
                registrationBean.setConfirmPassword(confirmPassword.toCharArray());
                return;
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        }
    }

    private void askAndSetEmail(RegistrationBean registrationBean) {
        while(true){
            String email = askInputOrBack("Email");
            try {
                registrationBean.setEmail(email);
                break;
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        }
    }
}

