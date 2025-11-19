package it.foodmood.controller.application;

import java.util.Arrays;

import it.foodmood.bean.LoginBean;
import it.foodmood.config.UserMode;
import it.foodmood.domain.model.Credential;
import it.foodmood.domain.model.User;
import it.foodmood.domain.value.Email;
import it.foodmood.exception.AuthenticationException;
import it.foodmood.persistence.dao.CredentialDao;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.UserDao;
import it.foodmood.utils.SessionManager;
import it.foodmood.utils.security.PasswordHasher;


public class LoginController {

    private final UserDao userDao;
    private final CredentialDao credentialDao;
    private final PasswordHasher passwordHasher;

    public LoginController(){
        this.userDao = DaoFactory.getInstance().getUserDao();
        this.credentialDao = DaoFactory.getInstance().getCredentialDao();
        this.passwordHasher = new PasswordHasher();
    }

    public User login(LoginBean loginBean, UserMode mode) throws AuthenticationException{
        
        // 1) Verifichiamo l'esistenza dell'utente
        Email email = new Email(loginBean.getEmail());
        User user = userDao.findByEmail(email).orElseThrow(() -> new AuthenticationException("Credenziali errate"));

        // 2) Carichiamo le credenziali
        Credential credential = credentialDao.findByUserId(user.getId());
        if(credential == null || credential.getPasswordHash() == null){
            throw new AuthenticationException("Credenziali errate.");
        }

        // 3) Verifica della password
        boolean isValidPassword = passwordHasher.verify(loginBean.getPassword(), credential.getPasswordHash());
        
        // 4) Pulizia password in memoria
        char[] password = loginBean.getPassword();
        if(password != null){
            Arrays.fill(password, '\0');
        }

        if(!isValidPassword){
            throw new AuthenticationException("Credenziali errate.");
        }

        // 5) Autorizzazione: il ruolo richiesto deve combaciare con la modalità dell'app
        boolean isAutorized = user.hasRole(mode.requireRole());

        if(!isAutorized){
            throw new AuthenticationException("Credenziali errate.");
        }

        // 5) Creo la sessione per l'utente
        SessionManager.getInstance().createSession(user);

        return user;
    }
}