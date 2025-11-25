package it.foodmood.infrastructure.bootstrap;

import it.foodmood.domain.model.Credential;
import it.foodmood.domain.model.Manager;
import it.foodmood.domain.value.Email;
import it.foodmood.domain.value.Person;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.utils.security.PasswordHasher;

public final class DemoBootstrap {
    private DemoBootstrap(){
        // costruttore vuoto
    }

    public static void initDemo(){
        var userDao = DaoFactory.getInstance().getUserDao();
        var credentialDao = DaoFactory.getInstance().getCredentialDao();
        PasswordHasher hasher = new PasswordHasher();

        Manager manager = new Manager(new Person("Mario", "Rossi"), new Email("mariorossi@email.com"));
        String password = "PasswordMario123";
        String hashPassword = hasher.hash(password.toCharArray());

        Credential credential = new Credential(manager.getId(), hashPassword);
    
        userDao.insert(manager);
        credentialDao.saveCredential(credential);
    }
}
