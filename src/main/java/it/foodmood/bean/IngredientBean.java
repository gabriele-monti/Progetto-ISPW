package it.foodmood.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IngredientBean {
    
    private String name;
    private MacronutriensBean macronutrients;
    private List<String> allergens = new ArrayList<>();

    public IngredientBean(){
        // Costruttore vuoto
    }

    // Getter
    public String getName() {return name;}

    public MacronutriensBean getMacronutrients(){ return macronutrients;}

    public List<String> getAllergens() {return Collections.unmodifiableList(allergens);}

    // Setter
    public void setName(String name){
        if(!isValidName(name)){
            throw new IllegalArgumentException("Il nome dell'ingrediente non deve essere vuoto.");
        }
        this.name = name.trim().toUpperCase();
    }

    public void setMacronutrients(MacronutriensBean macronutriens){
        this.macronutrients = macronutriens;
    }

    public void setAllergens(List<String> allergens){
        this.allergens = (allergens == null) ? new ArrayList<>() : validate(allergens);
    }

    public void addAllergen(String allergen){
        if(allergen == null || allergen.isBlank()) return;
        this.allergens.add(allergen.trim().toUpperCase());
    }

    // Metodi privati per la validazione sintattica
    private boolean isValidName(String name){
        return name != null && !name.isBlank() && name.length() <= 50;
    }

    private List<String> validate(List<String> allergen){
        List<String> out = new ArrayList<>(allergen.size());
        for(String s : allergen){
            if(s != null && !s.isBlank()) out.add(s.trim().toUpperCase());
        }
        return out;
    }
}
