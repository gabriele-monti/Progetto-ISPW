package it.foodmood.bean;

import java.util.Arrays;
import java.util.Objects;

import it.foodmood.utils.Validator;

public class RegistrationBean {

    private String name;
    private String surname;
    private String email;
    private char[] password;
    private char[] confirmPassword;

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

    public char[] getPassword(){
        return password;
    }

    public char[] getConfirmPassword(){
        return confirmPassword;
    }

    public void setName(String name){
        this.name = Objects.requireNonNull(name, "Il nome non deve essere vuoto").trim();
        if(this.name.isEmpty()){
            throw new IllegalArgumentException("Il nome non deve essere vuoto");
        }
    }

    public void setSurname(String surname){
        this.surname = Objects.requireNonNull(surname, "Il cognome non deve essere vuoto").trim();
        if(this.surname.isEmpty()){
            throw new IllegalArgumentException("Il cognome non deve essere vuoto");
        }
    }

    public void setEmail(String email){
        if(Validator.isValidEmail(email)){
            this.email = email.toLowerCase(); // Normalizzo la mail tutta in minuscolo per evitare duplicazioni dovute al maiuscolo e al minusco
        } else {
            throw new IllegalArgumentException("Email non valida.");
        }
    }

    public void setPassword(char[] password){
        if(Validator.isValidPassword(password)){
            this.password = password; 
        } else {
            Arrays.fill(password, '\0');
            throw new IllegalArgumentException("Password non valida.");
        }
    }

    public void setConfirmPassword(char[] confirmPassword){
        Objects.requireNonNull(confirmPassword, "Conferma password obbligatoria.");
        this.confirmPassword = confirmPassword;
    }
}
