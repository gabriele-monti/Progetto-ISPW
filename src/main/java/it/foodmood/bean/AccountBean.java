package it.foodmood.bean;

import java.util.Objects;

public class AccountBean {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$";

    private String name;
    private String surname;
    private String email;
    private String type;
    private int code;

    public AccountBean(){
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

    public String getType(){
        return type;
    }

    public int getCode(){
        return code;
    }

    public void setName(String name){
        this.name = Objects.requireNonNull(name, "Name");
    }

    public void setSurname(String surname){
        this.surname = Objects.requireNonNull(surname, "Cognome");
    }

    public void setEmail(String email){
        if(email == null || !email.matches(EMAIL_REGEX)){
            throw new IllegalArgumentException("Email non valida");
        }
        this.email = email;
    }

    public void setCode(int code){
        this.code = code;
    }

    public void setType(String type){
        this.type = type; 
    }
}
