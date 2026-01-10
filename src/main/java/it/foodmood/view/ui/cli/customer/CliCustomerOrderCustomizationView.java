package it.foodmood.view.ui.cli.customer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import it.foodmood.bean.AnswerBean;
import it.foodmood.bean.DishBean;
import it.foodmood.bean.ResponseBean;
import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.Budget;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.Kcal;
import it.foodmood.domain.value.StepType;
import it.foodmood.exception.CartException;
import it.foodmood.exception.OrderException;
import it.foodmood.view.boundary.CartBoundary;
import it.foodmood.view.boundary.CustomerOrderCustomizationBoundary;
import it.foodmood.view.ui.cli.ProtectedConsoleView;

public class CliCustomerOrderCustomizationView extends ProtectedConsoleView {
    
    private final CustomerOrderCustomizationBoundary orderCustomizationBoundary;
    private final CartBoundary cartBoundary;
    private final static String TITLE = "Ordina su misura per te";
    private static final Set<String> BASE_CATEGORIES = Set.of(DietCategory.TRADITIONAL.name(), DietCategory.VEGAN.name(), DietCategory.VEGETARIAN.name());
    
    public CliCustomerOrderCustomizationView(CustomerOrderCustomizationBoundary orderCustomizationBoundary, CartBoundary cartBoundary){
        super();
        this.orderCustomizationBoundary = orderCustomizationBoundary;
        this.cartBoundary = cartBoundary;
    }

    public void displayPage(){
        clearScreen();
        try {
            ResponseBean response = orderCustomizationBoundary.start();
            handleResponse(response);
        } catch (OrderException e) {
            showError(e.getMessage());
            return;
        }
    }

    private void handleResponse(ResponseBean response){
        if(response == null){
            showError("Errore nel sistema");
            return;
        }

        StepType nextStep = response.getNextStep();

        switch (nextStep) {
            case COURSE     -> askCourses();
            case DIET       -> askDietCateogory();
            case ALLERGENS  -> askAllergens(response);
            case KCAL       -> askKcal(response);
            case BUDGET     -> askBudget(response);
            case GENERATE   -> showPropose(response);
            default -> showError("Step non riconosciuto: " + nextStep);
        }
    }

    private void askCourses(){
        CourseType[] values = CourseType.values();
        Set<String> selected = new LinkedHashSet<>();

        while(true){
            clearScreen();
            try {
                showTitle(TITLE);
                showBold("Cosa vorresti ordinare?\n");

                for(int i = 0; i < values.length; i++){
                    String name = values[i].name();
                    String alredy = selected.contains(name) ? " (selezionata)" : "";
                    showInfo((i + 1) + ". " + values[i].description() + alredy);
                }
                int next = values.length + 1;
                showInfo(next + ". " + "Avanti");
                // showInfo("0. Indietro");

                String choice = askInput("\nSeleziona un'opzione: ");

                int index = Integer.parseInt(choice);

                if(index == next){
                    if(selected.isEmpty()){
                        showWarning("Seleziona almeno un opzione per andare avanti");
                        waitForEnter(null);
                        continue;
                    }
                    break;
                }

                if(index < 0 || index > next){
                    showError("Seleziona un opzione valida");
                    waitForEnter(null);
                    continue;
                }

                String courseName = values[index - 1].name();

                if(!selected.add(courseName)){
                    clearScreen();
                    showWarning("Hai già selezionato questa portata");
                    continue;
                }

                if(selected.size() == values.length){
                    showInfo("Hai selezionato tutte le portate disponibili");
                    waitForEnter("Premi INVIO per andare avanti");
                    break;
                }

                boolean again = askConfirmation("Vuoi aggiungere un'altra portata?");
                if(!again) break;    
            } catch (NumberFormatException e) {
                showError(e.getMessage());
            }            
        }
        submitAnswer(StepType.COURSE, selected);
    }

    private void askDietCateogory(){
        DietCategory[] values = DietCategory.values();
        Set<String> selected = new LinkedHashSet<>();

        while(true){
            clearScreen();
            showTitle(TITLE);
            showBold("Segui un particolare stile alimentare?\n");

            for(int i = 0; i < values.length; i++){
                String name = values[i].name();
                String alredy = selected.contains(name) ? " (selezionata)" : "";
                showInfo((i + 1) + ". " + values[i].description() + alredy);
            }

            int next = values.length + 1;
            showInfo(next + ". " + "Avanti");

            String choice = askInput("\nSeleziona un'opzione: ");

            int index;
            try {
                index = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                showError("Inserisci un numero valido.");
                waitForEnter(null);
                continue;
            }
                
            if(index == next){
                if(selected.isEmpty()){
                    showWarning("Seleziona almeno un opzione per andare avanti");
                    waitForEnter(null);
                    continue;
                }
                break;
            }

            if(index < 0 || index > next){
                showError("Seleziona un opzione valida");
                continue;
            }

            DietCategory chosen = values[index - 1];
            String key = chosen.name();

            if(BASE_CATEGORIES.contains(key)){
                selected.removeAll(BASE_CATEGORIES);
                selected.add(key);
            } else {
                if(!selected.add(key)){
                    selected.remove(key);
                }
            }
            
            if(selected.size() == 3){
                showInfo("Hai selezionato tutte le opzioni disponibili");
                waitForEnter("Premi INVIO per andare avanti");
                break;
            }

            boolean again = askConfirmation("Vuoi selezionare un'altra tipologia?");
            if(!again) break;    
           
        }
        submitAnswer(StepType.DIET, selected);
    }

    private void askAllergens(ResponseBean response){
        List<Allergen> options = List.copyOf(response.getAllergens());
        Set<String> selected = new LinkedHashSet<>();

        clearScreen();
        showTitle(TITLE);
        showBold("Hai allergie o intolleranze alimentari?\n");

        boolean confirm = askConfirmation("Risposta");
        if(!confirm){   
            submitAnswer(StepType.ALLERGENS, selected);
            return;
        }

        while(true){
            showTitle(TITLE);
            showBold("Seleziona gli allergeni\n");
            for(int i = 0; i < options.size(); i++){
                Allergen allergen = options.get(i);
                String key = allergen.name();
                String alredy = selected.contains(key) ? " (selezionato)" : "";
                if(i < 9){
                    showInfo((i + 1) + ".  " + allergen.description() + alredy);
                } else {
                    showInfo((i + 1) + ". " + allergen.description() + alredy);
                }
            }
                
            int next = options.size() + 1;
            showInfo(next + ". " + "Avanti");

            String choice = askInput("\nSeleziona un'opzione: ");

            int index = Integer.parseInt(choice);
                
            if(index < 0 || index > next){
                showError("Seleziona un opzione valida");
                waitForEnter(null);
                continue;
            }
                
            if(index == next){
                break;
            }

            Allergen chosen = options.get(index - 1);
            String key = chosen.name();

            if(!selected.add(key)){
                clearScreen();
                showWarning("Hai già selezionato questo allergene");
                continue;
            }

            if(selected.size() == options.size()){
                showInfo("Hai selezionato tutte le opzioni disponibili");
                waitForEnter("Premi INVIO per andare avanti");
                break;
            }

            boolean again = askConfirmation("Vuoi aggiungere un altro allergene?");
            if(!again) break;
        }
        submitAnswer(StepType.ALLERGENS, selected);
    }

    private void askKcal(ResponseBean responseBean){
        List<Integer> limits = responseBean.getValues();

        Integer selected = null;

        while(true){
            clearScreen();
            showTitle(TITLE);
            showBold("Vuoi che il tuo pasto rientri in un certo apporto calorico?\n");

            showInfo("1. " + Kcal.LIGHT.description() + " (fino a " + limits.get(0) + " Kcal)");
            showInfo("2. " + Kcal.BALANCED.description() + " (fino a " + limits.get(1) + " Kcal)");
            showInfo("3. " + Kcal.COMPLETE.description() + " (fino a " + limits.get(2) + " Kcal)");
            showInfo("4. " + Kcal.FREE.description());

            String choice = askInput("\nSeleziona un'opzione: ");

            int index;
            try {
                index = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                showError("Inserisci un numero valido.");
                waitForEnter(null);
                continue;
            }

            if(index < 1 || index > 4){
                showError("Seleziona un opzione valida");
                continue;
            }

            if(index == 4){
                submitIntegerAnswer(StepType.KCAL, selected);
                return;
            }

            selected = limits.get(index - 1);
            submitIntegerAnswer(StepType.KCAL, selected);
            return;
        }
    }

    private void askBudget(ResponseBean responseBean){
        List<Integer> limits = responseBean.getValues();

        Integer selected = null;

        while(true){
            clearScreen();
            showTitle(TITLE);
            showBold("Vuoi rimanere entro in un certo budget per il tuo pasto?\n");

            showInfo("1. " + Budget.ECONOMIC.description() + " (entro i " + limits.get(0) + " €)");
            showInfo("2. " + Budget.BALANCED.description() + " (entro i " + limits.get(1) + " €)");
            showInfo("3. " + Budget.PREMIUM.description() + " (entro i " + limits.get(2) + " €)");
            showInfo("4. " + Budget.FREE.description());

            String choice = askInput("\nSeleziona un'opzione: ");

            int index;
            try {
                index = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                showError("Inserisci un numero valido.");
                waitForEnter(null);
                continue;
            }

            if(index < 1 || index > 4){
                showError("Seleziona un opzione valida");
                continue;
            }

            if(index == 4){
                submitIntegerAnswer(StepType.BUDGET, selected);
                return;
            }

            selected = limits.get(index - 1);
            submitIntegerAnswer(StepType.BUDGET, selected);
            return;
        }
    }

    private void showPropose(ResponseBean responseBean){
        showTitle(TITLE);
        showBold("Abbiamo pensato di proporti: \n");

        List<DishBean> dishes = responseBean.getDishes();
        showProposeTable(dishes);

        if(!askConfirmation("Vuoi aggiungere un articolo all'ordine")){
            return;
        }

        addItems(dishes);
    }

    private void addItems(List<DishBean> dishes){
        while(true){
            String input = askInputOrBack("Inserisci il numero dell'articolo");
            Integer index = parseInteger(input, dishes.size());
            if(index == null ){
                showWarning("Input non valido");
                continue;
            }

            int quantity = askPositiveInt("Quantità: ");

            DishBean selected = dishes.get(index - 1);

            try {
                cartBoundary.addProduct(selected.getId(), quantity);
                showSuccess("Articolo '" + selected.getName() + "' aggiunto con successo.");

            } catch (CartException e) {
                showWarning(e.getMessage());
            }

            if(!askConfirmation("Vuoi aggiungere un altro articolo?")){
                return;
            }
        }
    }


    private void submitAnswer(StepType stepType, Set<String> answers){
        try {
            AnswerBean answerBean = new AnswerBean(stepType, answers);
            ResponseBean responseBean = orderCustomizationBoundary.submit(answerBean);
            handleResponse(responseBean);
        } catch (OrderException e) {
            showWarning(e.getMessage());
            waitForEnter("Premi INVIO per tornare al menù principale");
        }
    }

    private void submitIntegerAnswer(StepType stepType, Integer value){
        try {
            AnswerBean answerBean = new AnswerBean(stepType, value);
            ResponseBean responseBean = orderCustomizationBoundary.submit(answerBean);
            handleResponse(responseBean);
        } catch (OrderException e) {
            showWarning(e.getMessage());
            waitForEnter("Premi INVIO per tornare al menù principale");
        }
    }
}
