package it.foodmood.bean;

public class IngredientPortionBean {
    private IngredientBean ingredient;
    private double quantity;
    private String unit;

    public IngredientPortionBean(){
        // Costruttore vuoto
    }

    // Getter

    public IngredientBean getIngredient() {return ingredient;}

    public double getQuantity(){ return quantity;}

    public String getUnit(){ return unit;}

    // Setter

}
