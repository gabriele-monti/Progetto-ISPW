package it.foodmood.controller.application;

import java.util.Arrays;

import it.foodmood.bean.RegistrationBean;
import it.foodmood.domain.model.Credential;
import it.foodmood.domain.model.Customer;
import it.foodmood.domain.model.User;
import it.foodmood.domain.value.Email;
import it.foodmood.domain.value.Person;
import it.foodmood.exception.RegistrationException;
import it.foodmood.persistence.dao.CredentialDao;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.UserDao;
import it.foodmood.utils.security.PasswordHasher;

public class RegisterController {
    
    private final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private final CredentialDao credentialDao = DaoFactory.getInstance().getCredentialDao();
    private final PasswordHasher passwordHasher = new PasswordHasher();

    public void registerUser(RegistrationBean registrationBean) throws RegistrationException{
        try{
            // 1) Verifico se l'email è già registrata
            Email email = new Email(registrationBean.getEmail());
            if(userDao.findByEmail(email) != null){
                throw new RegistrationException("Email già registrata.");
            }

            // 2) Creazione del dominio
            Person person = new Person(registrationBean.getName(), registrationBean.getSurname());
            User newUser = new Customer(person, email);

            // 3) Hash della password
            String hashedPassword = passwordHasher.hash(registrationBean.getPassword());

            // 4) Creazione credenziali
            Credential credential = new Credential(newUser.getId(), hashedPassword);

            // 5) Salvataggio in persistenza
            userDao.save(newUser);
            credentialDao.saveCredential(credential);

        } catch (Exception e){
            throw new RegistrationException("Errore durante la registrazione.");
        } finally {
            // 7 Pulizia della password in memoria
            char[] passw = registrationBean.getPassword();
            if(passw != null){
                Arrays.fill(passw, '\0');
            }
        }
    }
}
