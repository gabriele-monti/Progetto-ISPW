package it.foodmood.bean;

public class ActorBean {
    String name;
    String surname;
    boolean guest;

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

    public void setGuest(boolean guest){
        this.guest = guest;
    }
}

