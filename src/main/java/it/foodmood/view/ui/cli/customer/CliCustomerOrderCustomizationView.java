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
    private static final String TITLE = "Ordina su misura per te";
    private static final String OPTION = "\nSeleziona un'opzione: "; 
    private static final String INVALID_OPTION = "Seleziona un'opzione valida"; 
    private static final String NEXT = "Premi INVIO per andare avanti";
    private static final String BACK_TO_HOME = "Premi INVIO per tornare al menù principale";
    private static final String UNTIL = " (fino a ";
    private static final String WITHIN = " (entro i ";
    private static final String KCAL = " Kcal)";
    private static final String EURO = " €)";


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

        boolean continueLoop = true;

        while(continueLoop){
            clearScreen();
            showTitle(TITLE);
            showBold("Cosa vorresti ordinare?\n");

            displayCourseOptions(values, selected);

            int next = values.length + 1;
            showInfo(next + ". " + "Avanti");
            continueLoop = handleCourseSelection(values, selected, next); 
        }
        submitAnswer(StepType.COURSE, selected);
    }

    private void displayCourseOptions(CourseType[] values, Set<String> selected){
        for(int i = 0; i < values.length; i++){
            String name = values[i].name();
            String alredy = selected.contains(name) ? " (selezionata)" : "";
            showInfo((i + 1) + ". " + values[i].description() + alredy);
        }
    }

    private boolean handleCourseSelection(CourseType[] values, Set<String> selected, int next){
        int index = askPositiveInt(OPTION);

        if(index == next){
            return handleNext(selected);
        }

        if(index > next){
            showError(INVALID_OPTION);
            waitForEnter(null);
            return true;
        }

        return processSelectedCourse(values, selected, index);
    }

    private boolean handleNext(Set<String> selected){
        if(selected.isEmpty()){
            showWarning("Seleziona almeno un'opzione per andare avanti");
            waitForEnter(null);
            return true;
        }
        return false;
    }

    private boolean processSelectedCourse(CourseType[] values, Set<String> selected, int index){
        String courseName = values[index - 1].name();

        if(!selected.add(courseName)){
            clearScreen();
            showWarning("Hai già selezionato questa portata");
            return true;
        }

        if(selected.size() == values.length){
            showInfo("Hai selezionato tutte le portate disponibili");
            waitForEnter(NEXT);
            return false;
        }

        return !askConfirmation("Vuoi aggiungere un'altra portata?");
    }

    private void askDietCateogory(){
        DietCategory[] values = DietCategory.values();
        Set<String> selected = new LinkedHashSet<>();

        boolean continueLoop = true;

        while(continueLoop){
            clearScreen();
            showTitle(TITLE);
            showBold("Segui un particolare stile alimentare?\n");

            displayDietOptions(values, selected);

            int next = values.length + 1;
            showInfo(next + ". " + "Avanti");

            continueLoop = handleDietSelection(values, selected, next);
        }
        submitAnswer(StepType.DIET, selected);
    }

    private boolean handleDietSelection(DietCategory[] values, Set<String> selected, int next){
        int index = askPositiveInt(OPTION);

        if(index == next){
            return handleNext(selected);
        }

        if(index > next){
            showError(INVALID_OPTION);
            waitForEnter(null);
            return true;
        }

        return processDietSelection(values, selected, index);
    }

    private boolean processDietSelection(DietCategory[] values, Set<String> selected, int index){
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
            waitForEnter(NEXT);
            return false;
        }

        return !askConfirmation("Vuoi selezionare un'altra tipologia?");
    }

    private void displayDietOptions(DietCategory[] values, Set<String> selected){
        for(int i = 0; i < values.length; i++){
            String name = values[i].name();
            String alredy = selected.contains(name) ? " (selezionata)" : "";
            showInfo((i + 1) + ". " + values[i].description() + alredy);
        }
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

        collectAllergenSelections(options, selected);
    
        submitAnswer(StepType.ALLERGENS, selected);
    }

    private void collectAllergenSelections(List<Allergen> options, Set<String> selected){
        boolean continueLoop = true;
        while(continueLoop){
            showTitle(TITLE);

            showBold("Seleziona gli allergeni\n");

            displayAllergenOptions(options, selected);

            int next = options.size() + 1;
            showInfo(next + ". " + "Avanti");

            continueLoop = handleAllergenSelection(options, selected, next);
        }
    }

    private boolean handleAllergenSelection(List<Allergen> options, Set<String> selected, int next){
        int index = askPositiveInt(OPTION);

        if(index < 0 || index > next){
            showError(INVALID_OPTION);
            waitForEnter(null);
            return true;
        }
            
        if(index == next){
            return false;
        }

        return processAllergenSelection(options, selected, index);
    }

    private boolean processAllergenSelection(List<Allergen> options, Set<String> selected, int index){
        Allergen chosen = options.get(index - 1);
        String key = chosen.name();

        if(!selected.add(key)){
            clearScreen();
            showWarning("Hai già selezionato questo allergene");
            return true;
        }

        if(selected.size() == options.size()){
            showInfo("Hai selezionato tutte le opzioni disponibili");
            waitForEnter(NEXT);
            return false;
        }

        return !askConfirmation("Vuoi aggiungere un altro allergene?");
    }


    private void displayAllergenOptions(List<Allergen> options, Set<String> selected){
        for(int i = 0; i < options.size(); i++){
            Allergen allergen = options.get(i);
            String key = allergen.name();
            String alredy = selected.contains(key) ? " (selezionato)" : "";
            String spacing = (i < 9) ? "  " : " "; 
            showInfo((i + 1) + "." + spacing + allergen.description() + alredy);
        }
    }

    private void askKcal(ResponseBean responseBean){
        List<Integer> limits = responseBean.getValues();
        Integer selected = selectKcalOption(limits);
        submitIntegerAnswer(StepType.KCAL, selected);
    }

    private Integer selectKcalOption(List<Integer> limits){
        clearScreen();
        showTitle(TITLE);
        showBold("Vuoi che il tuo pasto rientri in un certo apporto calorico?\n");
        
        showInfo("1. " + Kcal.LIGHT.description() + UNTIL + limits.get(0) + KCAL);
        showInfo("2. " + Kcal.BALANCED.description() + UNTIL + limits.get(1) + KCAL);
        showInfo("3. " + Kcal.COMPLETE.description() + UNTIL + limits.get(2) + KCAL);
        showInfo("4. " + Kcal.FREE.description());

        
        int index = askPositiveInt(OPTION);

        if(index > 4){
            showError(INVALID_OPTION);
            return selectKcalOption(limits);
        }

        return (index == 4) ? null : limits.get(index - 1);
    }

    private void askBudget(ResponseBean responseBean){
        List<Integer> limits = responseBean.getValues();
        Integer selected = selectBudgetOption(limits);
        submitIntegerAnswer(StepType.BUDGET, selected);
    }

    private Integer selectBudgetOption(List<Integer> limits){
        clearScreen();
        showTitle(TITLE);
        showBold("Vuoi rimanere entro in un certo budget per il tuo pasto?\n");

        showInfo("1. " + Budget.ECONOMIC.description() + WITHIN + limits.get(0) + EURO);
        showInfo("2. " + Budget.BALANCED.description() + WITHIN + limits.get(1) + EURO);
        showInfo("3. " + Budget.PREMIUM.description() + WITHIN + limits.get(2) + EURO);
        showInfo("4. " + Budget.FREE.description());
        
        int index = askPositiveInt(OPTION);

        if(index > 4){
            showError(INVALID_OPTION);
            return selectKcalOption(limits);
        }

        return (index == 4) ? null : limits.get(index - 1);
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

    private boolean addItems(List<DishBean> dishes){

        String input = askInputOrBack("Inserisci il numero dell'articolo");
        Integer index = parseInteger(input, dishes.size());

        if(index == null ){
            showWarning("Input non valido");
            return true;
        }

        int quantity = askPositiveInt("Quantità: ");
        DishBean selected = dishes.get(index - 1);

        try {
            cartBoundary.addProduct(selected.getId(), quantity);
            showSuccess("Articolo '" + selected.getName() + "' aggiunto con successo.");
        } catch (CartException e) {
            showWarning(e.getMessage());
        }

        return askConfirmation("Vuoi aggiungere un altro articolo?");

    }


    private void submitAnswer(StepType stepType, Set<String> answers){
        try {
            AnswerBean answerBean = new AnswerBean(stepType, answers);
            ResponseBean responseBean = orderCustomizationBoundary.submit(answerBean);
            handleResponse(responseBean);
        } catch (OrderException e) {
            showWarning(e.getMessage());
            waitForEnter(BACK_TO_HOME);
        }
    }

    private void submitIntegerAnswer(StepType stepType, Integer value){
        try {
            AnswerBean answerBean = new AnswerBean(stepType, value);
            ResponseBean responseBean = orderCustomizationBoundary.submit(answerBean);
            handleResponse(responseBean);
        } catch (OrderException e) {
            showWarning(e.getMessage());
            waitForEnter(BACK_TO_HOME);
        }
    }
}
