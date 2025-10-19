package it.foodmood.domain.valueobject;

/*
 * Tipologia della portata nel menù
 * Usato da: Dish (per classificare la tipologia del piatto)
 * GRASP: Information Expert -> l'elenco delle portate vive qui
 */

public enum CourseType {
    APPETIZER("Antipasto"), 
    FIRST_COURSE("Primo"), 
    MAIN_COURSE("Secondo"), 
    SIDE_DISH("Contorno"), 
    DESSERT("Dolce"),
    FRUIT("Frutta"), 
    BEVERAGE("Bevanda");

    private final String description;

    CourseType(String description){
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

