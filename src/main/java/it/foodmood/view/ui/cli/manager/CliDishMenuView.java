package it.foodmood.view.ui.cli.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import it.foodmood.bean.DishBean;
import it.foodmood.bean.IngredientPortionBean;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.DishState;
import it.foodmood.domain.value.Unit;
import it.foodmood.exception.BackRequestedException;
import it.foodmood.exception.DishException;
import it.foodmood.utils.UnitUtils;
import it.foodmood.view.boundary.DishBoundary;
import it.foodmood.view.boundary.IngredientBoundary;
import it.foodmood.view.ui.cli.ProtectedConsoleView;

public class CliDishMenuView extends ProtectedConsoleView {
    private final DishBoundary dishBoundary;
    private final IngredientBoundary ingredientBoundary;
    private final String INVALID_CHOICE = "Scelta non valida, riprova";

    public CliDishMenuView(DishBoundary dishBoundary, IngredientBoundary ingredientBoundary){
        this.dishBoundary = dishBoundary;
        this.ingredientBoundary = ingredientBoundary;
    }

    public void displayPage(){
        boolean back = false;

        while(!back){

            if(!ensureActiveSession()) return;

            clearScreen();
            showTitle("Gestisci Piatti");
            showInfo("0. Indietro");
            showInfo("1. Inserisci piatto");
            showInfo("2. Visualizza piatti");
            showInfo("3. Modifica piatto");
            showInfo("4. Elimina piatto");

            String choice = askInput("\nSeleziona un'opzione: ");

            switch(choice){
                case "0" -> back = true;
                case "1" -> createDish();
                case "2" -> readAllDishes();
                case "3" -> updateDish();
                case "4" -> deleteDish();
                default -> showError(INVALID_CHOICE);
            }
        }
    }

    private void createDish() {
        dishBoundary.ensureActiveSession();
        try {
            clearScreen();
            showTitle("Inserisci Piatto");
            showBold("Compila i campi");

            DishBean dishBean = new DishBean();

            String name = askInputOrBack("Nome");
            dishBean.setName(name);

            String description = askInputOrNull("Descrizione piatto (opzionale): ");
            dishBean.setDescription(description);

            CourseType courseType = askCourseType();
            dishBean.setCourseType(courseType);

            DietCategory dietCategory = askDietCategory();
            dishBean.setDietCategory(dietCategory);

            BigDecimal price = askBigDecimal("Prezzo: ");
            dishBean.setPrice(price);

            dishBean.setImageUri(null);

            DishState state = askDishState();
            dishBean.setState(state);

            requireIngredients(dishBean);

            dishBoundary.createDish(dishBean);

            showSuccess("Piatto inserito correttamente.");
            waitForEnter(null);
        } catch (DishException e) {
            showError(e.getMessage());
            waitForEnter("Premi INVIO per tornare indietro");

        } catch (IllegalArgumentException e){
            showError(e.getMessage());
        } catch (Exception e) {
            showError(e.getMessage());
            waitForEnter("Premi INVIO per tornare indietro");
        }
    }


    private void requireIngredients(DishBean dishBean) {
        boolean done = false;

        while(!done){
            showIngredients(dishBean);
            done = ingredientMenuChoice(dishBean);
        }
    }

    private void showIngredients(DishBean dishBean){
        clearScreen();
        showTitle("Ingrediente del piatto: " + dishBean.getName());

        List<IngredientPortionBean> selected = dishBean.getIngredients();
        if(selected == null || selected.isEmpty()){
            showWarning("Nessun ingrediente aggiunto");
        } else {
            showBold("Ingredienti presenti: ");
            showIngredientList(selected);
        }

        showInfo("\n0. Conferma");
        showInfo("1. Aggiungi ingrediente");
        showInfo("2. Rimuovi ultimo ingrediente");
    }

    private void showIngredientList(List<IngredientPortionBean> selected){
        for(int i = 0; i < selected.size(); i++){
            var ingredient = selected.get(i);
            String unitLabel = ingredient.getUnit().equals("GRAM") ? "g" : "ml";
            showInfo((i + 1) + ". " + ingredient.getIngredient().getName() + " - " + String.format("%.2f %s", ingredient.getQuantity(), unitLabel));
        }
    }

    private boolean ingredientMenuChoice(DishBean dishBean){
        String choice = askInput("Seleziona un'opzione: ");

        switch (choice) {
            case "0" :
                return confirmChoice(dishBean);
            case "1" : 
                addIngredientToDish(dishBean);
                return false;
            case "2": 
                removeLastIngredient(dishBean);
                return false;
            default:
                showError(INVALID_CHOICE);
                waitForEnter(null);
                return false;
        }
    }

    private boolean confirmChoice(DishBean dishBean){
        if(dishBean.getIngredients().isEmpty()){
            showWarning("Il piatto deve avere almeno un ingrediente");
            waitForEnter(null);
            return false;
        } 
        return true;
    }

    private void removeLastIngredient(DishBean dishBean){
        List<IngredientPortionBean> list = new ArrayList<>(dishBean.getIngredients());
        if(!list.isEmpty()){
            list.remove(list.size() - 1);
            dishBean.setIngredients(list);
        } else {
            showWarning("Nessun ingrediente da rimuovere");
            waitForEnter(null);
        }  
    }

    private void addIngredientToDish(DishBean dishBean){
        var ingredients = ingredientBoundary.getAllIngredients();

        if(ingredients == null || ingredients.isEmpty()){
            showWarning("Nessun ingrediente disponibile. Aggiungi prima degli ingredienti.");
            waitForEnter(null);
            return;
        }

        clearScreen();
        showTitle("Seleziona un ingrediente da aggiungere");

        tableIngredients();

        showInfo("0. Annulla");

        String input = askInput("\nInserisci il numero dell'ingrediente da aggiungere: ");

        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException _) {
            showError("Inserisci un numero valido.");
            waitForEnter(input);
            return;
        }

        if(index == 0) return;

        if(index < 1 || index > ingredients.size()){
            showError("Indice non valido.");
            waitForEnter(null);
            return;
        }

        var selected = ingredients.get(index - 1);

        Unit unit = selected.getUnit();

        String unitLabel = UnitUtils.toLabel(unit);

        double quantity = askDouble("Quantità in " + unitLabel + ": ");

        IngredientPortionBean portionBean = new IngredientPortionBean();
        portionBean.setIngredient(selected);
        portionBean.setQuantity(quantity);
        portionBean.setUnit(unit.name());

        dishBean.addIngredient(portionBean);

        showSuccess("Ingrediente aggiunto correttamente.");
        waitForEnter(null);
    }

    private void tableIngredients(){
        var ingredients = ingredientBoundary.getAllIngredients();

        if(ingredients == null || ingredients.isEmpty()){
            showWarning("Nessun ingrediente presente.");
            waitForEnter(null);
            return;
        }

        List<String> headers = List.of("N°", "Nome", "Unità", "Proteine", "Carboidrati", "Grassi", "Allergeni");

        List<List<String>> rows = IntStream.range(0, ingredients.size()).mapToObj(i -> {
            var ingredient = ingredients.get(i);
            String index = String.valueOf(i + 1);
            String name = ingredient.getName();
            String unit = ingredient.getUnit() == Unit.GRAM ? "g" : "ml";
            var macro = ingredient.getMacronutrients();
            String protein = String.format("%.1f", macro.getProtein());
            String carbs = String.format("%.1f", macro.getCarbohydrates());
            String fat = String.format("%.1f", macro.getFat());

            String allergens = (ingredient.getAllergens().isEmpty()) ? "-" : String.join(", ", ingredient.getAllergens());

            return List.of(index, name, unit, protein, carbs, fat, allergens);
        }).toList();

        List<Integer> columnWidths = List.of(4, 20,5,8,11,6,20);

        displayTable(headers, rows, columnWidths);
    }

    private DishState askDishState() {
        while(true){
            showBold("Stato del piatto: ");
            showInfo("1. Disponibile");
            showInfo("2. Non disponibile");

            String choice = askInput("\nSeleziona un'opzione: ");

            switch (choice){
                case "1" : 
                    return DishState.AVAILABLE;
                case "2" : 
                    return DishState.UNAVAILABLE;
                default: 
                    showError(INVALID_CHOICE);
                    continue;
            }
        }
    }

    private CourseType askCourseType(){
        while (true) {
            showBold("Tipologia portata: ");
            CourseType[] values = CourseType.values();
            for(int i = 0; i < values.length; i++){
                showInfo((i + 1) + ". " + values[i].description());
            }

            String choice = askInput("Seleziona una tipologia: ");

            try {
                int index = Integer.parseInt(choice) - 1;
                if(index >= 0 && index < values.length){
                    return values[index];
                }
            } catch (NumberFormatException _) {
                showError(INVALID_CHOICE);
            }
        }
    }

    private DietCategory askDietCategory(){
        while (true) {
            showBold("Categoria dietetica: ");
            DietCategory[] values = DietCategory.values();
            for(int i = 0; i < values.length; i++){
                showInfo((i + 1) + ". " + values[i].description());
            }

            String choice = askInput("Seleziona una categoria: ");

            try {
                int index = Integer.parseInt(choice) - 1;
                if(index >= 0 && index < values.length){
                    return values[index];
                }
            } catch (NumberFormatException _) {
                showError(INVALID_CHOICE);
            }
        }
    }

    private void readAllDishes() {
        clearScreen();
        showTitle("Lista Piatti");
        tableDishes();
        waitForEnter(null);
    }

    private void updateDish() {
        showInfo("Funzionalità non ancora implementata");
        waitForEnter(null);
    }

    private void deleteDish() {
        boolean choice = true;
        while (choice) {
            try {
                choice = deleteSingleDish();
            } catch (BackRequestedException _) {
                showInfo("Operazione annullata. Ritorno al menù ingredienti");
                choice = false;
                waitForEnter(null);
            } catch (DishException e){
                showError(e.getMessage());
                waitForEnter("Premi INVIO per riprovare");
            }
        }
    }

    private boolean deleteSingleDish() throws BackRequestedException, DishException{
        var dishes = dishBoundary.getAllDishes();
        clearScreen();
        showTitle("Elimina Piatto");
        tableDishes();

        String input = askInputOrBack("Inserisci il numero del piatto da eliminare");

        Integer index = parseDishIndex(input, dishes.size());

        if(index == null || index == 0){
            return false;
        }

        var selected = dishes.get(index - 1);
        String dishName = selected.getName();
        dishBoundary.deleteDish(dishName);
            
        showSuccess("Piatto '" + dishName + "' eliminato con successo.");

        boolean choice = askConfirmation("Vuoi eliminare un'altro piatto?");
        if(!choice){
            waitForEnter("Premi INVIO per continuare");
        }
        return choice;
    }

    private Integer parseDishIndex(String input, int dishSize){
        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException _) {
            showError("Inserisci un numero valido.");
            waitForEnter(input);
            return null;
        }

        if(index < 0 || index > dishSize){
            showError("Indice non valido.");
            waitForEnter(null);
            return null;
        }

        return index;
    }

    private void tableDishes(){
        var dishes = dishBoundary.getAllDishes();

        if(dishes == null || dishes.isEmpty()){
            showWarning("Nessun piatto presente.");
            waitForEnter(null);
            return;
        }

        List<String> headers = List.of("N°", "Nome", "Prezzo", "Stato", "Tipologia", "Categoria");

        List<List<String>> rows = IntStream.range(0, dishes.size()).mapToObj( i -> {
            var dish = dishes.get(i);

            String index = String.valueOf(i + 1);
            String name = dish.getName();
            String price = String.format("%.2f", dish.getPrice());
            String state = dish.getState().description();
            String courseType = dish.getCourseType().description();
            String dietCategory = dish.getDietCategory().description();

            return List.of(index, name, price, state, courseType, dietCategory);
        }).toList();

        List<Integer> columnWidths = List.of(2,25,7,15,15,15);

        displayTable(headers, rows, columnWidths);
    }
}
