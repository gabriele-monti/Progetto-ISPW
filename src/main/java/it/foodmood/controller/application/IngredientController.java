package it.foodmood.controller.application;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.foodmood.bean.IngredientBean;
import it.foodmood.bean.MacronutrientsBean;
import it.foodmood.domain.model.Ingredient;
import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.Unit;
import it.foodmood.domain.value.Macronutrients;
import it.foodmood.exception.IngredientException;
import it.foodmood.exception.PersistenceException;
import it.foodmood.exception.SessionExpiredException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.IngredientDao;
import it.foodmood.utils.SessionManager;

public class IngredientController {

    private final IngredientDao ingredientDao;
    private final SessionManager sessionManager = SessionManager.getInstance();

    public IngredientController(){
        this.ingredientDao = DaoFactory.getInstance().getIngredientDao();
    }

    public void createIngredient(IngredientBean ingredientBean) throws IngredientException{
        ensureActiveSession();

        if(ingredientBean == null) {
            throw new IngredientException("L'ingrediente non può essere nullo.");
        }
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

            if(protein == 0.0 && carbohydrate == 0.0 && fat == 0.0){
                throw new IngredientException("Deve esserci almeno un macronutriente > 0");
            }

            Unit unit = ingredientBean.getUnit();
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
            throw new IngredientException("Errore durante l'inserimento dell'ingrediente: " + e.getMessage());
        } catch (PersistenceException _) {
            throw new IngredientException("Errore tecnico durante l'inserimento dell'ingrediente");
        }
    }

    public List<IngredientBean> getAllIngredients() throws IngredientException{
        ensureActiveSession();
        try {
            return ingredientDao.findAll().stream().map(IngredientMapper::toBean).toList();
        } catch (PersistenceException e) {
            throw new IngredientException("Spiacenti si è verificato un errore tecnico durante il recupero degli ingredienti, riprovare in seguito.", e);
        }
    }

    public void deleteIngredient(String name) throws IngredientException{
        ensureActiveSession();
        if(name.isBlank()){
            throw new IngredientException("Il nome dell'ingrediente non può essere vuoto.");
        }

        try {
            if(ingredientDao.findById(name).isEmpty()){
                throw new IngredientException("Nessun ingrediente trovato con il nome: " + name);
            }
            ingredientDao.deleteById(name);
        
        } catch (PersistenceException _){
            throw new IngredientException("Errore tecnico durante l'eliminazione dell'ingrediente. Riprova più tardi");
        }
    }

    public Optional<IngredientBean> findIngredientByName(String name) throws IngredientException{
        ensureActiveSession();
        try {
            return ingredientDao.findById(name).map(IngredientMapper::toBean);
        } catch (PersistenceException _){
            throw new IngredientException("Errore tecnico durante il recuperòo dell'ingrediente. Riprova più tardi");
        }
    }

    private double normalize(Double value){
        return value == null ? 0.0 : value;
    }

    public void ensureActiveSession() throws IngredientException{
        try {
            sessionManager.requireActiveSession();
        } catch (SessionExpiredException e) {
            throw new IngredientException(e.getMessage());
        }
    }
}
