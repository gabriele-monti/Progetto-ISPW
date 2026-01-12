package it.foodmood.controller.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.foodmood.bean.AnswerBean;
import it.foodmood.bean.DishBean;
import it.foodmood.bean.ResponseBean;
import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.OrderWizardState;
import it.foodmood.domain.policy.AllergenFilterPolicy;
import it.foodmood.domain.policy.KcalPolicy;
import it.foodmood.domain.policy.KcalWeight;
import it.foodmood.domain.policy.OrderComplexityEvaluator;
import it.foodmood.domain.policy.PricePolicy;
import it.foodmood.domain.policy.PriceWeight;
import it.foodmood.domain.policy.WizardFlowPolicy;
import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.OrderComplexity;
import it.foodmood.domain.value.StepType;
import it.foodmood.exception.OrderException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.utils.SessionManager;

public class OrderCustomizationController {
    private final DishDao dishDao;
    private final SessionManager sessionManager; 
    
    private final AllergenFilterPolicy allergenPolicy;
    private final KcalPolicy kcalPolicy;
    private final PricePolicy pricePolicy;
    private final PriceWeight priceWeight;
    private final KcalWeight kcalWeight;
    private final WizardFlowPolicy flowPolicy;
    private final OrderComplexityEvaluator complexityEvaluator;
    private final DishMapper dishMapper;

    private OrderWizardState wizardState;
    private OrderComplexity complexity;

    public OrderCustomizationController(){
        DaoFactory factory = DaoFactory.getInstance();
        this.sessionManager = SessionManager.getInstance();
        this.dishDao = factory.getDishDao();

        this.allergenPolicy = new AllergenFilterPolicy();
        this.kcalPolicy = new KcalPolicy();
        this.pricePolicy = new PricePolicy();
        this.kcalWeight = new KcalWeight();
        this.priceWeight = new PriceWeight();
        this.flowPolicy = new WizardFlowPolicy();
        this.complexityEvaluator = new OrderComplexityEvaluator();
        this.dishMapper = new DishMapper();
    }

    public ResponseBean start() throws OrderException{
        ensureActiveSession();
        this.wizardState = new OrderWizardState();
        this.complexity = null;
        return new ResponseBean(StepType.COURSE);
    }
    
    public ResponseBean submit(AnswerBean answer) throws OrderException{
        ensureActiveSession();

        if(answer == null || answer.getStepType() == null){
            throw new OrderException("La risposta non possono essere nulla");
        }

        try {
            StepType currentStep = answer.getStepType();

            // Aggiorno le preferenze in base allo step corrente
            updatePreferences(currentStep, answer);

            // Se ho selezionato le portate, valuto la complessità della richiesta
            if(currentStep == StepType.COURSE){
                Set<CourseType> courses = wizardState.getCourseTypes();
                this.complexity = complexityEvaluator.evaluate(courses);
            }

            // Determino il prossimo step in base alle complessità
            StepType nextStep = flowPolicy.nextStep(currentStep, complexity);

            // Vedo se è il momento di generare le proposte
            if(nextStep == StepType.GENERATE){
                return generateProposals();
            }

            // Costruisco la risposta per lo step successivo
            return buildStepResponse(nextStep);
            
        } catch (Exception e){
            throw new OrderException("Errore durante l'elaborazione della risposta: " + e.getMessage());
        }
    }

    private ResponseBean buildStepResponse(StepType nextType){
        if(nextType == StepType.ALLERGENS && complexity == OrderComplexity.MODERATE){
            Set<Allergen> relevant = allergenPolicy.getAllergens(wizardState.getCourseTypes());
            return ResponseBean.forAllergens(nextType, relevant);
        }

        if(nextType == StepType.ALLERGENS && complexity == OrderComplexity.COMPLETE){
            return ResponseBean.forAllergens(nextType, EnumSet.allOf(Allergen.class));
        }

        if(nextType == StepType.BUDGET && complexity == OrderComplexity.COMPLETE){
            List<Integer> values = pricePolicy.budgetOption(wizardState.getCourseTypes().size());
            return ResponseBean.forValues(nextType, values);
        }

        if(nextType == StepType.KCAL && complexity == OrderComplexity.COMPLETE){
            List<Integer> values = kcalPolicy.kcalOptions(wizardState.getCourseTypes().size());
            return ResponseBean.forValues(nextType, values);
        }

        return new ResponseBean(nextType);
    }

    private ResponseBean generateProposals(){
        Set<CourseType> selectedCourses = wizardState.getCourseTypes();

        if(selectedCourses == null || selectedCourses.isEmpty()){
            throw new OrderException("Nessuna portata selezionata");
        }

        List<Dish> allFilteredDishes = new ArrayList<>();

        // Per ogni portata selezionata, recupera e filtra i piatti
        for(CourseType courseType : selectedCourses){
            List<Dish> dishesForCourse = dishDao.findByCourseType(courseType).stream()
                .filter(Dish::isAvailable)
                .filter(this::matchesDietCategory)
                .filter(this::isSafeForAllergens)
                .filter(dish -> isWithinKcalLimit(dish, courseType))
                .filter(dish -> isWithinBudgetLimit(dish, courseType))
                .toList();

            allFilteredDishes.addAll(dishesForCourse);
        }

        // Converto in bean
        List<DishBean> dishBeans = dishMapper.toBeans(allFilteredDishes);

        return ResponseBean.forProposals(StepType.GENERATE, dishBeans);
    }

    private boolean matchesDietCategory(Dish dish){
        Set<DietCategory> dietPreferences = wizardState.getDietCategories();
        if(dietPreferences == null || dietPreferences.isEmpty()){
            return true;
        }
        return dietPreferences.contains(dish.getDietCategory());
    }

    private boolean isSafeForAllergens(Dish dish){
        Set<Allergen> userAllergens = wizardState.getAllergens();
        if(userAllergens == null || userAllergens.isEmpty()){
            return true;
        }

        Set<Allergen> dishAllergens = dish.allergens();

        return Collections.disjoint(dishAllergens, userAllergens);
    }

    private boolean isWithinBudgetLimit(Dish dish, CourseType courseType){
        Integer totalBudget = wizardState.getBudgetPreference();
        if(totalBudget == null || totalBudget <= 0){
            return true;
        }

        Set<CourseType> selectedCourses = wizardState.getCourseTypes();
        double normalizedWeight = priceWeight.normalized(courseType, selectedCourses);
        double maxPriceForThisCourse = totalBudget * normalizedWeight;

        return dish.getPrice().toDouble() <= maxPriceForThisCourse;
    }

    private boolean isWithinKcalLimit(Dish dish, CourseType courseType){
        Integer totalKcal = wizardState.getKcalPreference();
        if(totalKcal == null || totalKcal <= 0){
            return true;
        }

        Set<CourseType> selectedCourses = wizardState.getCourseTypes();
        double normalizedWeight = kcalWeight.normalized(courseType, selectedCourses);
        double maxKcalForThisCourse = totalKcal * normalizedWeight;

        return dish.getKcal() <= maxKcalForThisCourse;
    }

    private void updatePreferences(StepType step, AnswerBean preference) throws OrderException{

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
    
    public void ensureActiveSession(){
        sessionManager.requireActiveSession();
    }
}
