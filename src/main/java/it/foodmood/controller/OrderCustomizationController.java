package it.foodmood.controller;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.foodmood.bean.AnswerBean;
import it.foodmood.bean.DishBean;
import it.foodmood.bean.ResponseBean;
import it.foodmood.controller.mapper.DishMapper;
import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.OrderWizardState;
import it.foodmood.domain.policy.AllergenFilterPolicy;
import it.foodmood.domain.policy.KcalPolicy;
import it.foodmood.domain.policy.OrderComplexityEvaluator;
import it.foodmood.domain.policy.PricePolicy;
import it.foodmood.domain.policy.WizardFlowPolicy;
import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.OrderComplexity;
import it.foodmood.domain.value.StepType;
import it.foodmood.exception.OrderException;
import it.foodmood.exception.SessionExpiredException;
import it.foodmood.utils.SessionManager;

/*
  Application Controller per la gestione del wizard di raccolta preferenze per l'ordine
*/
public class OrderCustomizationController {

    private final SessionManager sessionManager; 
    
    private final AllergenFilterPolicy allergenFilterPolicy;
    private final KcalPolicy kcalPolicy;
    private final PricePolicy pricePolicy;
    private final WizardFlowPolicy wizardFlowPolicy;
    private final OrderComplexityEvaluator complexityEvaluator;
    private final DishMapper dishMapper;

    private final DishProposals dishProposals;

    private OrderWizardState wizardState;
    private OrderComplexity currentComplexity;

    public OrderCustomizationController(){
        this.sessionManager = SessionManager.getInstance();

        this.allergenFilterPolicy = new AllergenFilterPolicy();
        this.kcalPolicy = new KcalPolicy();
        this.pricePolicy = new PricePolicy();

        this.dishProposals = new DishProposals();

        this.wizardFlowPolicy = new WizardFlowPolicy();
        this.complexityEvaluator = new OrderComplexityEvaluator();
        this.dishMapper = new DishMapper();
    }

    
    public ResponseBean start() throws OrderException{
        ensureActiveSession();
        initializeWizardState();

        ResponseBean response = new ResponseBean();
        response.setNextStep(StepType.COURSE);
        return response;
    }
    
    // Processo la risposta dell'utente e determino il prossimo step
    public ResponseBean submit(AnswerBean answer) throws OrderException{
        ensureActiveSession();

        validateAnswer(answer);

        StepType currentStep = answer.getStepType();

        try {
            // Aggiorno le preferenze in base allo step corrente
            updatePreferences(currentStep, answer);

            // Se ho selezionato le portate, valuto la complessità della richiesta
            if(currentStep == StepType.COURSE){
                Set<CourseType> courses = wizardState.getCourseType();
                this.currentComplexity = complexityEvaluator.evaluate(courses);
            }

            // Determino il prossimo step in base alle complessità
            StepType nextStep = wizardFlowPolicy.nextStep(currentStep, currentComplexity);

            // Vedo se è il momento di generare le proposte
            if(nextStep == StepType.GENERATE){
                return generateProposals();
            }

            // Costruisco la risposta per lo step successivo
            return buildStepResponse(nextStep);
            
        } catch (IllegalArgumentException e) {
            throw new OrderException("Risposta non valida: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new OrderException("Stato non valido: " + e.getMessage(), e);
        }
    }

    private void initializeWizardState(){
        this.wizardState = new OrderWizardState();
        this.currentComplexity = null;
    }

    private void validateAnswer(AnswerBean answer) throws OrderException{
        if(answer == null){
            throw new OrderException("La risposta non può essere nulla");
        }
        if (answer.getStepType() == null){
            throw new OrderException("Il tipo di step non può essere nullo");
        }
    }

    private ResponseBean buildStepResponse(StepType nextType){
        ResponseBean response = new ResponseBean();
        response.setNextStep(nextType);

        if(nextType == StepType.ALLERGENS && currentComplexity == OrderComplexity.MODERATE){
            Set<Allergen> relevant = allergenFilterPolicy.getAllergens(wizardState.getCourseType());
            response.setAllergens(relevant);
            return response;
        }

        if(nextType == StepType.ALLERGENS && currentComplexity == OrderComplexity.COMPLETE){
            response.setAllergens(EnumSet.allOf(Allergen.class));
            return response;
        }

        if(nextType == StepType.BUDGET && currentComplexity == OrderComplexity.COMPLETE){
            List<Integer> values = pricePolicy.budgetOption(wizardState.getCourseType().size());
            response.setValues(values);
            return response;
        }

        if(nextType == StepType.KCAL && currentComplexity == OrderComplexity.COMPLETE){
            List<Integer> values = kcalPolicy.kcalOptions(wizardState.getCourseType().size());
            response.setValues(values);
            return response;
        }

        return response;
    }

    private ResponseBean generateProposals() throws OrderException{
        Set<CourseType> selectedCourses = wizardState.getCourseType();

        if(selectedCourses == null || selectedCourses.isEmpty()){
            throw new OrderException("Nessuna portata selezionata");
        }

        List<Dish> allFilteredDishes = dishProposals.generate(wizardState);

        // Converto in bean
        List<DishBean> dishBeans = dishMapper.toBeans(allFilteredDishes);

        ResponseBean responseBean = new ResponseBean();
        responseBean.setNextStep(StepType.GENERATE);
        responseBean.setDishes(dishBeans);
        return responseBean;
    }

    private void updatePreferences(StepType step, AnswerBean preference){

        switch(step){
            case COURSE -> { 
                Set<CourseType> courses = preference.getAnswers().stream().map(CourseType::fromName).collect(Collectors.toSet());
                wizardState.setCourseType(courses); 
            }
            case DIET -> { 
                Set<DietCategory> diet = preference.getAnswers().stream().map(DietCategory::fromName).collect(Collectors.toSet());
                wizardState.setDietCategory(diet); 

            }
            case ALLERGENS -> { 
                Set<Allergen> allergens = preference.getAnswers().stream().map(Allergen::fromName).collect(Collectors.toSet());
                wizardState.setAllergens(allergens);
            }
            case BUDGET -> { 
                Integer budget = preference.getValue();
                wizardState.setBudgetPreference(budget);
            }
            case KCAL -> { 
                Integer kcal = preference.getValue();
                wizardState.setKcalPreference(kcal);
            }
            default -> throw new IllegalStateException("Stato non valido: " + step);
        }
    }

    private void ensureActiveSession() throws OrderException{
        try {
            sessionManager.requireActiveSession();
        } catch (SessionExpiredException _) {
            throw new OrderException("Sessione scaduta.");
        }
    }
}