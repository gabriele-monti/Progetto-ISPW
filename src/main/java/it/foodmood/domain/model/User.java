package it.foodmood.domain.model;

import java.util.Objects;
import java.util.UUID;

import it.foodmood.domain.value.Email;
import it.foodmood.domain.value.Person;

public final class User {

    private final UUID id;
    private Person person;
    private Email email;
    private Role role;

    public User(Person person, Email email,Role role) {
        this.id = UUID.randomUUID();
        this.person = Objects.requireNonNull(person, "L'utente è obbligatorio.");
        this.email = Objects.requireNonNull(email, "L'email è obbligatoria.");
        this.role = Objects.requireNonNull(role, "Ruolo obbligatorio");
    }

    public Person getPerson() {
        return person;
    }

    public Email getEmail() {
        return email;
    }

    public UUID getId() {
        return id;
    }

    public Role getRole(){
        return role;
    }

    // metodi di aggiornamento
    public void changePerson(Person newPerson){
        this.person = Objects.requireNonNull(newPerson);
    }

    public void changeEmail(Email newEmail){
        this.email = Objects.requireNonNull(newEmail);
    }

    public void changeRole(Role newRole){
        this.role = Objects.requireNonNull(newRole);
    }

    @Override
    public String toString(){
        return "Utente {id = " + id +
                ", " + person +
                ", email = " + email + 
                ", ruolo = " + role + " }";
    }
}
