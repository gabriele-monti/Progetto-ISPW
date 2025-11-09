package it.foodmood.bean;

import it.foodmood.utils.Validator;

public class LoginBean {
    private String email;
    private String password;

    private LoginBean(){}

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setEmail(String email){
        if(Validator.isValidEmail(email)){
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email non valida");
        }
    }
}
