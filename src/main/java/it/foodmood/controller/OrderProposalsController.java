package it.foodmood.controller;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.foodmood.application.SessionManager;
import it.foodmood.bean.AnswerBean;
import it.foodmood.bean.DishBean;
import it.foodmood.bean.ResponseBean;
import it.foodmood.controller.mapper.DishMapper;
import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.OrderFlowState;
import it.foodmood.domain.policy.AllergenFilterPolicy;
import it.foodmood.domain.policy.KcalPolicy;
import it.foodmood.domain.policy.OrderComplexityEvaluator;
import it.foodmood.domain.policy.PricePolicy;
import it.foodmood.domain.policy.FlowPolicy;
import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.OrderComplexity;
import it.foodmood.domain.value.StepType;
import it.foodmood.exception.OrderException;

/*
  Application Controller per la gestione del flusso di raccolta preferenze dell'utente
*/

public class OrderProposalsController {

    private final SessionManager sessionManager; 

    public OrderProposalsController(){
        this.sessionManager = SessionManager.getInstance();
    }
    
    public ResponseBean start(){
        initializeFlow();

        ResponseBean response = new ResponseBean();
        response.setNextStep(StepType.COURSE);
        return response;
    }
    
    // Processo la risposta dell'utente e determino il prossimo step
    public ResponseBean submit(AnswerBean answer) throws OrderException{

        validateAnswer(answer);

        StepType currentStep = answer.getStepType();

        try {
            OrderFlowState flowState = sessionManager.getOrderFlowState();
            OrderComplexity currentComplexity = sessionManager.getOrderComplexity();

            // Aggiorno le preferenze in base allo step corrente
            updatePreferences(flowState, currentStep, answer);

            // Se ho selezionato le portate, valuto la complessità della richiesta
            if(currentStep == StepType.COURSE){
                Set<CourseType> courses = flowState.getCourseType();
                OrderComplexityEvaluator evaluator = new OrderComplexityEvaluator();
                currentComplexity = evaluator.evaluate(courses);
                sessionManager.setOrderComplexity(currentComplexity);
            }

            // Determino il prossimo step in base alle complessità
            FlowPolicy flowPolicy = new FlowPolicy();
            StepType nextStep = flowPolicy.nextStep(currentStep, currentComplexity);

            // Vedo se è il momento di generare le proposte
            if(nextStep == StepType.GENERATE){
                return generateProposals(flowState);
            }

            // Costruisco la risposta per lo step successivo
            return buildStepResponse(nextStep, flowState, currentComplexity);
            
        } catch (IllegalArgumentException e) {
            throw new OrderException("Risposta non valida: " + e.getMessage(), e);
        } catch (IllegalStateException e) {
            throw new OrderException("Stato non valido: " + e.getMessage(), e);
        }
    }

    private void initializeFlow(){
        OrderFlowState flowState = new OrderFlowState();
        sessionManager.setOrderFlowState(flowState);
        sessionManager.setOrderComplexity(null);
    }

    private void validateAnswer(AnswerBean answer) throws OrderException{
        if(answer == null){
            throw new OrderException("La risposta non può essere nulla");
        }
        if (answer.getStepType() == null){
            throw new OrderException("Il tipo di step non può essere nullo");
        }
    }

    private ResponseBean buildStepResponse(StepType nextType, OrderFlowState flowState, OrderComplexity currentComplexity){
        ResponseBean response = new ResponseBean();
        response.setNextStep(nextType);

        if(nextType == StepType.ALLERGENS && currentComplexity == OrderComplexity.MODERATE){
            AllergenFilterPolicy allergenFilterPolicy = new AllergenFilterPolicy();
            Set<Allergen> relevant = allergenFilterPolicy.getAllergens(flowState.getCourseType());
            response.setAllergens(relevant);
            return response;
        }

        if(nextType == StepType.ALLERGENS && currentComplexity == OrderComplexity.COMPLETE){
            response.setAllergens(EnumSet.allOf(Allergen.class));
            return response;
        }

        if(nextType == StepType.BUDGET && currentComplexity == OrderComplexity.COMPLETE){
            PricePolicy pricePolicy = new PricePolicy();
            List<Integer> values = pricePolicy.budgetOption(flowState.getCourseType().size());
            response.setValues(values);
            return response;
        }

        if(nextType == StepType.KCAL && currentComplexity == OrderComplexity.COMPLETE){
            KcalPolicy kcalPolicy = new KcalPolicy();
            List<Integer> values = kcalPolicy.kcalOptions(flowState.getCourseType().size());
            response.setValues(values);
            return response;
        }

        return response;
    }

    private ResponseBean generateProposals(OrderFlowState flowState) throws OrderException{
        Set<CourseType> selectedCourses = flowState.getCourseType();

        if(selectedCourses == null || selectedCourses.isEmpty()){
            throw new OrderException("Nessuna portata selezionata");
        }

        DishProposals dishProposals = new DishProposals();
        List<Dish> allFilteredDishes = dishProposals.generate(flowState);

        // Converto in bean
        DishMapper dishMapper = new DishMapper();
        List<DishBean> dishBeans = dishMapper.toBeans(allFilteredDishes);

        ResponseBean responseBean = new ResponseBean();
        responseBean.setNextStep(StepType.GENERATE);
        responseBean.setDishes(dishBeans);
        return responseBean;
    }

    private void updatePreferences(OrderFlowState flowState, StepType step, AnswerBean preference){

        switch(step){
            case COURSE -> { 
                Set<CourseType> courses = preference.getAnswers().stream().map(CourseType::fromName).collect(Collectors.toSet());
                flowState.setCourseType(courses); 
            }
            case DIET -> { 
                Set<DietCategory> diet = preference.getAnswers().stream().map(DietCategory::fromName).collect(Collectors.toSet());
                flowState.setDietCategory(diet); 

            }
            case ALLERGENS -> { 
                Set<Allergen> allergens = preference.getAnswers().stream().map(Allergen::fromName).collect(Collectors.toSet());
                flowState.setAllergens(allergens);
            }
            case BUDGET -> { 
                Integer budget = preference.getValue();
                flowState.setBudgetPreference(budget);
            }
            case KCAL -> { 
                Integer kcal = preference.getValue();
                flowState.setKcalPreference(kcal);
            }
            default -> throw new IllegalStateException("Stato non valido: " + step);
        }
    }
}