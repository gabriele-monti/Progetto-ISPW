package it.foodmood.domain.valueobject;

import java.util.Objects;

/*
 * Value Object dei macronutrienti riferiti a 100g/ml di prodotto
 */

public final class Macronutrients {

    public static final int BASE_AMOUNT = 100; // 100 g o 100 ml 
    
    private final double protein;
    private final double carbohydrates;
    private final double fat;

    private Macronutrients(double protein, double carbohydrates, double fat){
        if (protein < 0 || carbohydrates < 0 || fat < 0){
            throw new IllegalArgumentException("I macronutrienti devono essere >= 0");
        }
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
    }

    // Factory method per creare macronutrienti per 100 g/ml 
    public static Macronutrients of(double protein, double carbohydrates, double fat){
        return new Macronutrients(protein, carbohydrates, fat);
    }

    public double protein() {return protein;}
    public double carbohydrates() {return carbohydrates;}
    public double fat() {return fat;}

    // Calcolo kcal
    public double kcal(){
        return  4 * protein + 4 * carbohydrates + 9 * fat;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(!(o instanceof Macronutrients m)) return false;
        return Double.compare(m.protein, protein) == 0 && 
               Double.compare(m.carbohydrates, carbohydrates) == 0 &&  
               Double.compare(m.fat, fat) == 0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(protein, carbohydrates, fat);
    }

    @Override
    public String toString(){
        return "Proteine: %.1fg, Carboidrati: %.1fg, Grassi: %.1fg (per %dg/ml)".formatted(protein, carbohydrates, fat, BASE_AMOUNT);
    }

}
