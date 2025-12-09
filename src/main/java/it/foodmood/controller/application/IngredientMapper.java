package it.foodmood.controller.application;

import it.foodmood.bean.IngredientBean;
import it.foodmood.bean.MacronutrientsBean;
import it.foodmood.domain.model.Ingredient;
import it.foodmood.domain.value.Allergen;

public final class IngredientMapper {
    
    private IngredientMapper(){
        // costruttore vuoto
    }

    public static IngredientBean toBean(Ingredient ingredient){
        IngredientBean ingredientBean = new IngredientBean();
        ingredientBean.setName(ingredient.getName());

        MacronutrientsBean macronutrientsBean = new MacronutrientsBean();
        macronutrientsBean.setProtein(ingredient.getMacro().getProtein());
        macronutrientsBean.setCarbohydrates(ingredient.getMacro().getCarbohydrates());
        macronutrientsBean.setFat(ingredient.getMacro().getFat());

        ingredientBean.setMacronutrients(macronutrientsBean);

        ingredientBean.setUnit(ingredient.getUnit());

        ingredientBean.setAllergens(ingredient.getAllergens().stream().map(Allergen::description).toList());

        return ingredientBean;
    }
}
