package it.foodmood.bean;

import java.util.List;
import java.util.Set;

import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.StepType;

public class ResponseBean {
    private final StepType nextStep;
    private Set<Allergen> allergens;
    private List<Integer> values;
    private List<DishBean> dishes;

    public ResponseBean(StepType nextStep){
        this.nextStep = nextStep;
    }

    public static ResponseBean forAllergens(StepType nextStep, Set<Allergen> allergens){
        ResponseBean responseBean = new ResponseBean(nextStep);
        responseBean.allergens = allergens;
        return responseBean;
    }

    public static ResponseBean forValues(StepType nextStep, List<Integer> values){
        ResponseBean responseBean = new ResponseBean(nextStep);
        responseBean.values = values;
        return responseBean;
    }

    public static ResponseBean forProposals(StepType nextStep, List<DishBean> dishes){
        ResponseBean responseBean = new ResponseBean(nextStep);
        responseBean.dishes = dishes;
        return responseBean;
    }

    public StepType getNextStep(){
        return nextStep;
    }

    public Set<Allergen> getAllergens(){
        return allergens;
    }

    public List<Integer> getValues(){
        return values;
    }

    public List<DishBean> getDishes(){
        return dishes;
    }
}
