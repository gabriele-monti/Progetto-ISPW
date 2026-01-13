package it.foodmood.view.boundary;

import java.util.List;
import java.util.Optional;

import it.foodmood.bean.IngredientBean;
import it.foodmood.controller.application.IngredientController;
import it.foodmood.exception.IngredientException;

public class IngredientBoundary {

    private final IngredientController controller;

    public IngredientBoundary(){
        this.controller = new IngredientController();
    }

    public void createIngredient(IngredientBean ingredientBean) throws IngredientException {
        controller.createIngredient(ingredientBean);
    }

    public List<IngredientBean> getAllIngredients() {
        return controller.getAllIngredients();
    }    
    
    public void deleteIngredient(String name) throws IngredientException {
        controller.deleteIngredient(name);
    }    
    
    public Optional<IngredientBean> findIngredientByName(String name) {
        return controller.findIngredientByName(name);
    }

    public void ensureActiveSession(){
        controller.ensureActiveSession();
    }
}
