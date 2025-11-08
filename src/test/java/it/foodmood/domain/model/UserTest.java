package it.foodmood.domain.model;

import it.foodmood.ui.utils.security.PasswordHasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private final PasswordHasher hasher = new PasswordHasher();

    @Test
    void createUser(){
        String password = "userPass";
        String passwordHash = hasher.hash(password);

        // Creo il cliente
        Customer customer = new Customer("Mario", "Rossi", "mariorossi@example.it");
        Credential credential = new Credential(customer.getId(), passwordHash);

        assertEquals("Mario", customer.getName());
        assertEquals("Rossi", customer.getSurname());
        assertEquals("mariorossi@example.it", customer.getEmail());

        assertTrue(hasher.verify(password, credential.getPasswordHash()));
        assertFalse(hasher.verify("password", credential.getPasswordHash()));
    }

    @Test
    void createWaiter(){
        String password = "waiterPass";
        String passwordHash = hasher.hash(password);

        // Creo il cliente
        Waiter waiter = new Waiter("Luigi", "Verdi", "luigiverdi@example.it");
        Credential credential = new Credential(waiter.getId(), passwordHash);

        assertEquals("Luigi", waiter.getName());
        assertEquals("Verdi", waiter.getSurname());
        assertEquals("luigiverdi@example.it", waiter.getEmail());

        assertTrue(hasher.verify(password, credential.getPasswordHash()));
        assertFalse(hasher.verify("password", credential.getPasswordHash()));
    }

    @Test
    void createManager(){
        String password = "managerPass";
        String passwordHash = hasher.hash(password);

        // Creo il cliente
        Manager manager = new Manager("Elisa", "Bianchi", "elisabianchi@example.it");
        Credential credential = new Credential(manager.getId(), passwordHash);

        assertEquals("Elisa", manager.getName());
        assertEquals("Bianchi", manager.getSurname());
        assertEquals("elisabianchi@example.it", manager.getEmail());

        assertTrue(hasher.verify(password, credential.getPasswordHash()));
        assertFalse(hasher.verify("password", credential.getPasswordHash()));
    }
}
