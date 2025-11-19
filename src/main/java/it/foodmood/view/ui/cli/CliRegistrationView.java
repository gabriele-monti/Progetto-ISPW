package it.foodmood.view.ui.cli;

import it.foodmood.bean.RegistrationBean;
import it.foodmood.exception.RegistrationException;
import it.foodmood.view.boundary.RegistrationBoundary;

public class CliRegistrationView extends ConsoleView {
    
    private static final String TITLE = "REGISTRAZIONE";

    private final RegistrationBoundary boundary;

    public CliRegistrationView(RegistrationBoundary boundary){
        this.boundary = boundary;
    }

    @Override
    public CliPages displayPage(){
        while(true){
            showTitle(TITLE);
            showInfo("Inserisci i dati (0 = indietro)\n");

            RegistrationBean registrationBean = new RegistrationBean();

            String name = askInputOrBack("Nome: ");
            registrationBean.setName(name);

            String surname = askInputOrBack("Cognome: ");
            registrationBean.setSurname(surname);

            String email;

            while(true){
                email = askInputOrBack("Email: ");
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
                password = askInputOrBack("Password: ");
                confirmPassword = askInputOrBack("Conferma password: ");

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
                waitForEnter(null);
                return CliPages.EXIT;
            } catch (RegistrationException e) {
                showError(e.getMessage());
                waitForEnter(null);
            }
        }
    }
}

