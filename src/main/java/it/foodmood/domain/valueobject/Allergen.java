package it.foodmood.domain.valueobject;

/*
 * Elenco ufficiale dei 14 allergeni da dichiarare secondo (Reg. UE 1169/2011 + D.Lgs. 231/2017)
 * 
 * -Value Object: rappresenta un valore immutabile del dominio
 * -Usato da: Ingredient, Dish, ProductCommercial
 * -GRASP: Information Expert -> la logica degli allergeni vive qui e non altrove.
 */

public enum Allergen {
    GLUTEN("Cereali contenenti glutine"),
    CRUSTACEANS("Crostacei e prodotti derivati"),
    EGGS("Uova e derivati"),
    FISH("Pesce e derivati"),
    PEANUTS("Arachidi e derivati"),
    SOY("Soia e derivati"),
    MILK("Latte e derivati, incluso lattosio"),
    NUTS("Frutta a guscio (mandorle, nocciole, noci, ecc)"),
    CELERY("Sedano e derivati"),
    MUSTARD("Senape e derivati"),
    SESAME("Semi di sesamo e derivati"),
    SULPHITES("Anidrite solforosa e solfiti >10mg/kg o litro"),
    LUPIN("Lupini e derivati"),
    MOLLUSCS("Molluschi e derivati");

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
