package it.foodmood.bean;

import it.foodmood.domain.value.Role;

public class AuthenticationBean {
    private final String token;
    private final Role role;

    public AuthenticationBean(String token, Role role){
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public Role getRole() {
        return role;
    }
    
}
