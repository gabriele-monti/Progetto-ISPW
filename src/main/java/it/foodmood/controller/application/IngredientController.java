package it.foodmood.controller.application;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.foodmood.bean.IngredientBean;
import it.foodmood.bean.MacronutrientsBean;
import it.foodmood.domain.model.Ingredient;
import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.Unit;
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
        try {
            String name = ingredientBean.getName();
            MacronutrientsBean macronutrientsBean = ingredientBean.getMacronutrients();

            if(macronutrientsBean == null || macronutrientsBean.isEmpty()){
                throw new IngredientException("L'ingrediente deve avere almeno un macronutriente");
            }

            // converto i macronutrienti in 0.0 qualora fossero null
            double protein = normalize(macronutrientsBean.getProtein());
            double carbohydrate = normalize(macronutrientsBean.getCarbohydrates());
            double fat = normalize(macronutrientsBean.getFat());

            String unitStr = ingredientBean.getUnit();

            Unit unit = Unit.valueOf(unitStr);

            Macronutrients macronutrients = new Macronutrients(protein, carbohydrate, fat);

            // converto gli allergeni
            Set<Allergen> allergens = ingredientBean.getAllergens().stream().filter(s -> s != null && !s.isBlank())
                        .map(String::trim)
                        .map(String::toUpperCase)
                        .map(Allergen::valueOf)
                        .collect(Collectors.toSet());

            Ingredient ingredient = new Ingredient(name, macronutrients, unit, allergens);

            if(ingredientDao.findById(name).isPresent()){
                throw new IngredientException("Esiste già un ingrediente con il nome: " + name);
            }

            // inserisco l'ingrediente
            ingredientDao.insert(ingredient);
        } catch (IllegalArgumentException e) {
            throw new IngredientException(e.getMessage());
        }
        
    }

    public List<IngredientBean> getAllIngredients(){
        return ingredientDao.findAll().stream().map(this::toBean).collect(Collectors.toList());
    }

    public void deleteIngredient(String name){
        ingredientDao.deleteById(name);
    }

    private IngredientBean toBean(Ingredient ingredient){
        IngredientBean ingredientBean = new IngredientBean();
        ingredientBean.setName(ingredient.getName());

        MacronutrientsBean macronutrientsBean = new MacronutrientsBean();
        macronutrientsBean.setProtein(ingredient.getMacro().getProtein());
        macronutrientsBean.setCarbohydrates(ingredient.getMacro().getCarbohydrates());
        macronutrientsBean.setFat(ingredient.getMacro().getFat());

        ingredientBean.setMacronutrients(macronutrientsBean);

        ingredientBean.setUnit(ingredient.getUnit().name());

        ingredientBean.setAllergens(ingredient.getAllergens().stream().map(Allergen::name).collect(Collectors.toList()));

        return ingredientBean;
    }

    private double normalize(Double value){
        return value == null ? 0.0 : value;
    }
}
