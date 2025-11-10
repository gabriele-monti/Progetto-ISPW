package it.foodmood.bean;

import it.foodmood.utils.Validator;

public class LoginBean {
    private String email;
    private char[] password;

    public LoginBean(){}

    public String getEmail(){
        return email;
    }

    public char[] getPassword(){
        return password;
    }

    public void setPassword(char[] password){
        this.password = password;
    }

    // validazione sintattica
    public void setEmail(String email){
        if(Validator.isValidEmail(email)){
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email non valida");
        }
    }
}
