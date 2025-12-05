package it.foodmood.domain.value;

/*
 * Elenco ufficiale dei 14 allergeni da dichiarare secondo (Reg. UE 1169/2011 + D.Lgs. 231/2017)
 * 
 * -Value Object: rappresenta un valore immutabile del dominio
 * -Usato da: Ingredient, Dish, ProductCommercial
 * -GRASP: Information Expert -> la logica degli allergeni vive qui e non altrove.
 */

public enum Allergen {
    GLUTEN("Glutine"),
    CRUSTACEANS("Crostacei"),
    EGGS("Uova"),
    FISH("Pesce"),
    PEANUTS("Arachidi"),
    SOY("Soia"),
    MILK("Latte"),
    NUTS("Frutta a guscio"),
    CELERY("Sedano"),
    MUSTARD("Senape"),
    SESAME("Semi di sesamo "),
    SULPHITES("Anidrite solforosa"),
    LUPIN("Lupini"),
    MOLLUSCS("Molluschi");

    private final String description;

    Allergen(String description){
        this.description = description;
    }

    public String description(){
        return description;
    }

    @Override
    public String toString(){
        return name() + " - " + description;
    }
}
