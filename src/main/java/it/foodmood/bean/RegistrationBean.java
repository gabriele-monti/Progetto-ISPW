package it.foodmood.bean;

import java.util.Objects;

import it.foodmood.utils.Validator;

public class RegistrationBean {

    private String name;
    private String surname;
    private String email;
    private String password;

    public RegistrationBean(){
        // Costruttore vuoto
    }

    public String getName(){
        return name; 
    }

    public String getSurname(){
        return surname;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void setName(String name){
        this.name = Objects.requireNonNull(name, "Il nome non deve essere vuoto");
    }

    public void setSurname(String surname){
        this.surname = Objects.requireNonNull(surname, "Il cognome non deve essere vuoto");
    }

    public void setEmail(String email){
        if(Validator.isValidEmail(email)){
            this.email = email.toLowerCase(); // Normalizzo la mail tutta in minuscolo per evitare duplicazioni dovute al maiuscolo e al minusco
        } else {
            throw new IllegalArgumentException("Email non valida.");
        }
    }

    public void setPassword(String password){
        if(Validator.isValidPassword(password)){
            this.password = password; 
        } else {
            throw new IllegalArgumentException("Password non valida.");
        }
    }
}
