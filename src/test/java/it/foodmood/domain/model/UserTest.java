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
        String password = "userPass";
        String passwordHash = hasher.hash(password);

        // Creo il cliente
        User user = new User(new Person("Nome", "Cognome"), new Email("nome.cognome@foodmood.it"), Role.CUSTOMER);
        
        Credential credential = new Credential(user.getId(), passwordHash);

        assertEquals("Nome", user.getPerson().firstName());
        assertEquals("Cognome", user.getPerson().lastName());
        assertEquals("nome.cognome@foodmood.it", user.getEmail().email());
        assertEquals(Role.CUSTOMER, user.getRole());

        assertTrue(hasher.verify(password, credential.getPasswordHash()));
        assertFalse(hasher.verify("password", credential.getPasswordHash()));
    }

    @Test
    void changeRole(){
        User user = new User(new Person("Mario", "Rossi"), new Email("mario.rossi@example.it"), Role.WAITER);
        user.changeRole(Role.MANAGER);
        assertEquals(Role.MANAGER, user.getRole());
    }
}
