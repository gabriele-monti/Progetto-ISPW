package it.foodmood.controller.application;

import java.util.Set;
import java.util.stream.Collectors;

import it.foodmood.bean.IngredientBean;
import it.foodmood.bean.MacronutrientsBean;
import it.foodmood.domain.model.Ingredient;
import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.Macronutrients;
import it.foodmood.exception.IngredientException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.IngredientDao;


public class IngredientController {
    private final IngredientDao ingredientDao;

    public IngredientController(){
        this.ingredientDao = DaoFactory.getInstance().getIngredientDao();
    }

    public void createIngredient(IngredientBean ingredientBean) throws IngredientException{
        // Ingredient ingredient;

        String name = ingredientBean.getName();

        MacronutrientsBean macronutrientsBean = ingredientBean.getMacronutrients();

        if(macronutrientsBean == null || macronutrientsBean.isEmpty()){
            throw new IngredientException("L'ingrediente deve avere almeno un macronutriente");
        }

        // converto i macronutrienti in 0.0 qualora fossero null
        double protein = normalize(macronutrientsBean.getProtein());
        double carbohydrate = normalize(macronutrientsBean.getCarbohydrates());
        double fat = normalize(macronutrientsBean.getFat());

        Macronutrients macronutrients = new Macronutrients(protein, carbohydrate, fat);

        // converto gli allergeni
        Set<Allergen> allergens = ingredientBean.getAllergens().stream().filter(strings -> strings != null && strings.isBlank())
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .map(Allergen::valueOf)
                    .collect(Collectors.toSet());
        
        Ingredient ingredient = new Ingredient(name, macronutrients, allergens);

        if(ingredientDao.findById(name).isPresent()){
            throw new IngredientException("Esiste già un ingrediente con il nome: " + name);
        }

        // inserisco l'ingrediente
        ingredientDao.insert(ingredient);
    }

    private double normalize(Double value){
        return value == null ? 0.0 : value;
    }
}
