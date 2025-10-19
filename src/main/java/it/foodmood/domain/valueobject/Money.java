package it.foodmood.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money {
    private final BigDecimal amount;

    private Money(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("L'importo deve essere > 0");
        }
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static Money of(double amount){
        return new Money(BigDecimal.valueOf(amount));
    }

    public static Money of(BigDecimal amount){
        return new Money(amount);
    }

    public BigDecimal amount() { return amount;}

    public Money add(Money other){
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other){
        return new Money(this.amount.subtract(other.amount));
    }

    // Metodo utile per moltiplicare l'importo per un fattore (prezzo x quantità)
    public Money multiply(double factor){
        return new Money(this.amount.multiply(BigDecimal.valueOf(factor)));
    }

    public Money divide(double divisor){
        if(divisor == 0){
            throw new ArithmeticException("Attenzione, non puoi dividere per 0!");
        }
        BigDecimal result = this.amount.divide(BigDecimal.valueOf(divisor), 2, RoundingMode.HALF_UP);
        return new Money(result);
    }

    @Override
    public String toString(){
        return amount + " €";
    }
}
