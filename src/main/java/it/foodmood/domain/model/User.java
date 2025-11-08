package it.foodmood.domain.model;

import java.util.Objects;
import java.util.UUID;

public abstract class User {

    private final UUID id;
    private String name;
    private String surname;
    private String email;

    // Costruttore protected, solo le sottoclassi possono creare istanze
    protected User(String name, String surname, String email) {
        this.id = UUID.randomUUID();
        this.name = Objects.requireNonNull(name, "Il nome non può essere nullo.");
        this.surname = Objects.requireNonNull(surname, "Il cognome non può essere nullo.");
        this.email = Objects.requireNonNull(email, "Email non può essere nulla.");
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail(){
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
