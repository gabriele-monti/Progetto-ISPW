package it.foodmood.view.ui.cli.manager;

import java.util.List;

import it.foodmood.bean.IngredientBean;
import it.foodmood.bean.MacronutrientsBean;
import it.foodmood.controller.application.IngredientController;
import it.foodmood.exception.BackRequestedException;
import it.foodmood.exception.IngredientException;
import it.foodmood.view.ui.cli.ConsoleView;

public class CliIngredientMenuView extends ConsoleView {
    private final IngredientController controller;

    public CliIngredientMenuView(IngredientController controller){
        this.controller = controller;
    }

    public void displayPage(){
        boolean back = false;

        while(!back){
            clearScreen();
            showTitle("Gestisci Ingredienti");
            showInfo("0. Indietro");
            showInfo("1. Inserisci ingrediente");
            showInfo("2. Visualizza ingredienti");
            showInfo("3. Modifica ingrediente");
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
        boolean choice = true;
        while (choice) {
            try {
                clearScreen();
                showTitle("Elimina Ingrediente");
                tableIngredients();
                    
                String name = askInputOrBack("Inserisci il nome dell'ingrediente da eliminare: ");
                controller.deleteIngredient(name.toUpperCase());
                    
                showSuccess("Ingrediente eliminato con successo.");

                choice = askConfirmation("Vuoi eliminare un'altro ingrediente?");

                if(!choice){
                    waitForEnter("Premi INVIO per continuare");
                }
                    
            } catch (BackRequestedException _) {
                showInfo("Operazione annullata. Ritorno al menù ingredienti");
                waitForEnter(null);
            } catch (IngredientException e) {
                showError(e.getMessage());
                waitForEnter("Premi INVIO per riprovare");                
            }
        }
    }

    private void updateIngredient() {
        showInfo("Funzionalità non ancora implementata");
        waitForEnter(null);
    }

    private void readAllIngredient() {
        clearScreen();
        showTitle("Lista Ingredienti");
        tableIngredients();
        waitForEnter(null);
    }

    private void createIngredient() {
        try {
            clearScreen();
            while(true){
                showTitle("Inserisci ingrediente");
                showBold("Compila i campi (0 = indietro)");

                IngredientBean ingredientBean = new IngredientBean();
                MacronutrientsBean macronutrientsBean = new MacronutrientsBean();

                String name = askInputOrBack("Nome: ");
                ingredientBean.setName(name);

                String unit = null;

                while(unit == null){
                    showBold("\nUnità di misura");
                    showInfo("1. Grammi (g)\n2. Milliletri (ml)");
                    String choice = askInput("\nSeleziona un'opzione: ");

                    switch(choice){
                        case "1" -> unit = "GRAM";
                        case "2" -> unit = "MILLILITER";
                        default -> showError("Scelta non valida, riprova.");
                    }
                }
                ingredientBean.setUnit(unit);

                boolean created = requireMacronutrients(ingredientBean, macronutrientsBean);
                if(created){
                    return;
                }
            }
        } catch (BackRequestedException _) {
            showInfo("Operazione annullata. Ritorno al menù ingredienti");
            waitForEnter(null);
        }
    }

    private boolean requireMacronutrients(IngredientBean ingredientBean, MacronutrientsBean macronutrientsBean){
        try {
            showBold("\nInserisci i macronutrienti riferiti a 100 g/ml di prodotto");
            showInfo("Inserisci almeno un macronutriente, (0 != indietro)");
            double protein = askDouble("Proteine: ");
            double carbohydrates = askDouble("Carboidrati: ");
            double fat = askDouble("Grassi: ");

            macronutrientsBean.setProtein(protein);
            macronutrientsBean.setCarbohydrates(carbohydrates);
            macronutrientsBean.setFat(fat);

            ingredientBean.setMacronutrients(macronutrientsBean);

            controller.createIngredient(ingredientBean);

            showSuccess("Ingrediente inseristo correttamente.");
            waitForEnter(null);
            return true;

        } catch (IngredientException e) {
            showError(e.getMessage());
            waitForEnter("Premi INVIO per riprovare");       
            return false;         
        }
    } 

    private void tableIngredients(){
        var ingredients = controller.getAllIngredients();

        if(ingredients == null || ingredients.isEmpty()){
            showWarning("Nessun ingrediente presente.");
            waitForEnter(null);
            return;
        }

        List<String> headers = List.of("Nome", "Unità", "Proteine", "Carboidrati", "Grassi", "Allergeni");

        List<List<String>> rows = ingredients.stream().map(ingredient -> {
            String name = ingredient.getName();
            String unit = ingredient.getUnit().equals("GRAM") ? "g" : "ml";
            var macro = ingredient.getMacronutrients();
            String protein = String.format("%.1f", macro.getProtein());
            String carbs = String.format("%.1f", macro.getCarbohydrates());
            String fat = String.format("%.1f", macro.getFat());

            String allergens = (ingredient.getAllergens().isEmpty()) ? "-" : String.join(", ", ingredient.getAllergens());

            return List.of(name, unit, protein, carbs, fat, allergens);
        }).toList();

        List<Integer> columnWidths = List.of(20,5,8,11,6,20);

        displayTable(headers, rows, columnWidths);
    }
}
