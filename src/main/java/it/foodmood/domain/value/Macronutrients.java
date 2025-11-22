package it.foodmood.domain.value;

import java.util.Objects;

public final class Macronutrients {

    public static final int BASE_AMOUNT = 100; // 100 g o 100 ml 
    
    private final double protein;
    private final double carbohydrates;
    private final double fat;

    public Macronutrients(double protein, double carbohydrates, double fat){
        if (protein < 0 || carbohydrates < 0 || fat < 0){
            throw new IllegalArgumentException("I macronutrienti devono essere >= 0");
        }
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
    }

    public double getProtein() {return protein;}
    public double getCarbohydrates() {return carbohydrates;}
    public double getFat() {return fat;}

    // Calcolo kcal
    public double kcal(){
        return  4 * protein + 4 * carbohydrates + 9 * fat;
    }

    public Macronutrients scale(double ratio){
        if(!Double.isFinite(ratio) || ratio <= 0){
            throw new IllegalArgumentException("Il rapporto deve essere maggiore di 0!");
        }
        return new Macronutrients(protein * ratio, carbohydrates * ratio, fat * ratio);
    }

    // Somma macronutrienti
    public Macronutrients plus(Macronutrients other){
        Objects.requireNonNull(other, "Non puoi sommare macronutrienti 'null'");
        return new Macronutrients(this.protein + other.protein, this.carbohydrates + other.carbohydrates, this.fat + other.fat);
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
