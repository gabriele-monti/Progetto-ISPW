package it.foodmood.bean;

public class IngredientBean {
    
    private String name;

    public IngredientBean(){
        // Costruttore vuoto
    }

    // Getter

    public String getName() {return name;}

    // Setter
    public void setName(String name){
        if(!isValidName(name)){
            throw new IllegalArgumentException("Il nome dell'ingrediente non deve essere vuoto.");
        }
        this.name = name.trim().toUpperCase();
    }

    // Metodi privati per la validazione sintattica
    private boolean isValidName(String name){
        return name != null && !name.isBlank() && name.length() <= 50;
    }
}
