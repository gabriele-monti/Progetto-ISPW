package it.foodmood.persistence.dao;

import it.foodmood.domain.model.Credential;
import it.foodmood.domain.model.User;

public interface RegistrationDao {
    
    // Creo un nuovo utente
    void saveUser(User user);

    // Salvo le credenziali
    void saveCredential(Credential credential);

    // Verifico se esiste già una mail associata
    boolean existsByEmail(String email);
}
