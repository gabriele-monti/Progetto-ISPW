package it.foodmood.controller;

import java.util.Arrays;

import it.foodmood.application.SessionManager;
import it.foodmood.bean.ActorBean;
import it.foodmood.bean.LoginBean;
import it.foodmood.config.UserMode;
import it.foodmood.domain.model.Credential;
import it.foodmood.domain.model.User;
import it.foodmood.domain.value.Email;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.exception.PersistenceException;
import it.foodmood.persistence.dao.CredentialDao;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.UserDao;
import it.foodmood.utils.security.PasswordHasher;

public class LoginController {

    private static final String INCORRECT_CREDENTIALS = "Credenziali errate.";

    private final UserDao userDao;
    private final CredentialDao credentialDao;
    private final PasswordHasher passwordHasher;

    public LoginController(){
        this.userDao = DaoFactory.getInstance().getUserDao();
        this.credentialDao = DaoFactory.getInstance().getCredentialDao();
        this.passwordHasher = new PasswordHasher();
    }

    public ActorBean login(LoginBean loginBean, UserMode mode) throws AuthenticationException{

        try {

            // 1) Verifichiamo l'esistenza dell'utente
            Email email = new Email(loginBean.getEmail());
            User user = userDao.findByEmail(email).orElseThrow(() -> new AuthenticationException(INCORRECT_CREDENTIALS));

            // 2) Carichiamo le credenziali
            Credential credential = credentialDao.findByUserId(user.getId());
            if(credential == null || credential.passwordHash() == null){
                throw new AuthenticationException(INCORRECT_CREDENTIALS);
            }

            // 3) Verifica della password
            boolean isValidPassword = passwordHasher.verify(loginBean.getPassword(), credential.passwordHash());

            // 4) Pulizia password in memoria
            char[] password = loginBean.getPassword();
            if(password != null){
                Arrays.fill(password, '\0');
            }

            if(!isValidPassword){
                throw new AuthenticationException(INCORRECT_CREDENTIALS);
            }

            // 5) Autorizzazione: il ruolo richiesto deve combaciare con la modalità dell'app
            boolean isAutorized = user.hasRole(mode.requireRole());

            if(!isAutorized){
                throw new AuthenticationException(INCORRECT_CREDENTIALS);
            }

            // 5) Creo la sessione per l'utente
            SessionManager.getInstance().createUserSession(user);

            ActorBean actor = new ActorBean();

            actor.setName(user.getPerson().firstName());
            actor.setSurname(user.getPerson().lastName());
            actor.setGuest(false);
            actor.setLogged(true);

            return actor;

        } catch (IllegalArgumentException _) {
            throw new AuthenticationException(INCORRECT_CREDENTIALS);
        } catch (PersistenceException e){
            throw new AuthenticationException("Errore tecnico durante il login. Riprova più tardi.", e);
        } finally {
            char[] password = loginBean.getPassword();
            if(password != null){
                Arrays.fill(password, '\0');
            }
        }
    }

    public void logout(){
        SessionManager.getInstance().terminateCurrentSession();
    }
}