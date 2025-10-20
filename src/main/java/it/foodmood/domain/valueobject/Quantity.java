package it.foodmood.domain.valueobject;

import java.util.Objects;

public final class Quantity implements Comparable<Quantity>{

    public static final int BASE_AMOUNT = Macronutrients.BASE_AMOUNT;

    private final double amount;
    private final Unit unit;

    private Quantity(double amount, Unit unit){
        if(!Double.isFinite(amount) || amount <= 0.0){
            throw new IllegalArgumentException("La quantità deve essere maggiore di 0.");
        }
        this.unit = Objects.requireNonNull(unit, "L'unità non può essere nulla!");
        this.amount = amount;
    } 

    public static Quantity of(double amount, Unit unit){
        return new Quantity(amount, unit);
    }

    public static Quantity grams(double amount){
        return new Quantity(amount, Unit.GRAM);
    }

    public static Quantity milliliters(double amount){
        return new Quantity(amount, Unit.MILLILITER);
    }

    public double amount() {return amount;}
    public Unit unit() {return unit;}

    // Metodo per moltiplicare la quantità per un fattore (es. dimezza, raddoppia, ...)
    public Quantity scale(double factor){
        if(!Double.isFinite(factor) || factor <= 0.0){
            throw new IllegalArgumentException("Il fattore deve essere maggiore di zero e finito.");
        }
        return new Quantity(amount * factor, unit);
    }

    // Metodo usato per sommare le quantità della stessa unità
    public Quantity plus(Quantity other){
        requireSameUnit(other);
        return new Quantity(this.amount + other.amount, this.unit);
    }

    // Metodo usato per sottrare le quantità dell stessa unità
    public Quantity minus(Quantity other){
        requireSameUnit(other);
        double result = this.amount - other.amount;
        if (result <= 0.0) {
            throw new IllegalArgumentException("Il risultato deve essere maggiore di 0");
        }
        return new Quantity(result, this.unit);
    }

    public double ratioToBase(){
        return amount / BASE_AMOUNT;
    }

    // Metodo usato per confrontare quantità della stessa unità
    public int compareTo(Quantity other){
        requireSameUnit(other);
        return Double.compare(this.amount, other.amount);
    }

    private void requireSameUnit(Quantity other){
        Objects.requireNonNull(other, "La quantità da confrontare non può essere nulla");
        if(other == null || this.unit != other.unit){
            throw new IllegalArgumentException("Unità incompatibili: " + this.unit + "vs" + other.unit);
        }
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Quantity q)) return false;
        return Double.compare(q.amount, amount) == 0 && unit == q.unit; 
    }

    @Override
    public int hashCode(){
        return Objects.hash(amount, unit);
    }

    @Override
    public String toString(){
        return "%.2f %s".formatted(amount, unit == Unit.GRAM ? "g" : "ml");
    }
}