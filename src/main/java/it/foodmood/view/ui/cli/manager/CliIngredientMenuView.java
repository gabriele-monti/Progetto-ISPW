package it.foodmood.view.ui.cli.manager;

import it.foodmood.controller.application.IngredientController;
import it.foodmood.view.ui.cli.ConsoleView;

public class CliIngredientMenuView extends ConsoleView {
    private final IngredientController controller;

    public CliIngredientMenuView(IngredientController controller){
        this.controller = controller;
    }

    public void displayPage(){
        boolean back = false;

        while(!back){
            showTitle("Gestisci Ingredienti");
            showInfo("0. Indietro");
            showInfo("1. Inserisci ingrediente");
            showInfo("2. Modifica ingrediente");
            showInfo("3. Visualizza ingredienti");
            showInfo("4. Elimina ingrediente");

            String choice = askInput("\nSeleziona un'opzione: ");

            switch(choice){
                case "0" -> back = true;
                case "1" -> createIngredient();
                case "2" -> readAllIngredient();
                case "3" -> updateIngredient();
                case "4" -> deleteIngredient();
                default -> showError("Scelta non valida, riprova.");
            }
        }
    }

    private void deleteIngredient() {
        showInfo("Funzionalità non ancora implementata");
    }

    private void updateIngredient() {
        showInfo("Funzionalità non ancora implementata");
    }

    private void readAllIngredient() {
        showInfo("Funzionalità non ancora implementata");
    }

    private void createIngredient() {
        showInfo("Funzionalità non ancora implementata");
    }
}
