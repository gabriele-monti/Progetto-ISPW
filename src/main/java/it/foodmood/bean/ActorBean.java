package it.foodmood.bean;

public class ActorBean {
    private String name;
    private String surname;
    private boolean guest;
    private boolean logged;

    public ActorBean(){
        // Costruttore vuoto
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public boolean isGuest(){
        return guest;
    }

    public boolean isLogged(){
        return logged;
    }

    public void setGuest(boolean guest){
        this.guest = guest;
    }

    public void setLogged(boolean logged){
        this.logged = logged;
    }
}

