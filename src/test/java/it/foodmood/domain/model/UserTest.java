package it.foodmood.domain.model;

import org.junit.jupiter.api.Test;

import it.foodmood.domain.value.Email;
import it.foodmood.domain.value.Person;
import it.foodmood.domain.value.Role;
import it.foodmood.utils.security.PasswordHasher;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private final PasswordHasher hasher = new PasswordHasher();

    @Test
    void createCustomerUser(){
        char[] password = "userPass".toCharArray();
        String passwordHash = hasher.hash(password);

        // Creo il cliente
        User user = new Customer(new Person("Nome", "Cognome"), new Email("nome.cognome@foodmood.it"));
        
        Credential credential = new Credential(user.getId(), passwordHash);

        assertEquals("Nome", user.getPerson().firstName());
        assertEquals("Cognome", user.getPerson().lastName());
        assertEquals("nome.cognome@foodmood.it", user.getEmail().email());
        assertEquals(Role.CUSTOMER, user.getRole());

        assertTrue(hasher.verify(password, credential.getPasswordHash()));
        assertFalse(hasher.verify("password".toCharArray(), credential.getPasswordHash()));
    }
}
