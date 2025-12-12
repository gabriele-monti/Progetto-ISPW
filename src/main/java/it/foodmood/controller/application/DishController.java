package it.foodmood.controller.application;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import it.foodmood.bean.DishBean;
import it.foodmood.bean.IngredientBean;
import it.foodmood.bean.IngredientPortionBean;
import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.Ingredient;
import it.foodmood.domain.validation.DietCategoryValidator;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.DishState;
import it.foodmood.domain.value.Image;
import it.foodmood.domain.value.IngredientPortion;
import it.foodmood.domain.value.Money;
import it.foodmood.domain.value.Quantity;
import it.foodmood.domain.value.Unit;
import it.foodmood.exception.DishException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.persistence.dao.IngredientDao;
import it.foodmood.utils.SessionManager;

public class DishController {

    private final DishDao dishDao;
    private final IngredientDao ingredientDao;
    private final SessionManager sessionManager = SessionManager.getInstance();

    public DishController(){
        DaoFactory factory = DaoFactory.getInstance();
        this.dishDao = factory.getDishDao();
        this.ingredientDao = factory.getIngredientDao();
    }

    public void createDish(DishBean dishBean) throws DishException{
        ensureActiveSession();

        if(dishBean == null) {
            throw new DishException("Il piatto non può essere nullo.");
        }

        try {
            String name = dishBean.getName();
            String description = dishBean.getDescription();
            BigDecimal priceValue = dishBean.getPrice();
            CourseType courseType = dishBean.getCourseType();
            DietCategory dietCategory = dishBean.getDietCategory();
            String imageUri = dishBean.getImageUri();
            DishState dishState = dishBean.getState();

            // controllo se esiste già un piatto con questo nome
            if(dishDao.findById(name).isPresent()){ 
                throw new DishException("Esiste già un piatto con il nome: " + name);
            }

            if(dishBean.getIngredients() == null || dishBean.getIngredients().isEmpty()){
                throw new DishException("Il piatto deve avere almeno un ingrediente.");
            }

            Money price = new Money(priceValue);

            Image image = (imageUri != null && !imageUri.isBlank()) ? new Image(URI.create(imageUri)) : null;

            List<IngredientPortion> ingredientPortions = toDomainIngredientPortions(dishBean.getIngredients());

            Dish dish = new Dish(name, description, courseType, dietCategory, ingredientPortions, dishState, image, price);

            DietCategoryValidator.validate(dish);

            dishDao.insert(dish);

        } catch (DishException e){
            throw e;
        } catch (IllegalArgumentException e){
            throw new DishException("Errore durante l'inserimento del piatto: " + e.getMessage());
        }
    }

    public List<DishBean> getAllDishes(){
        ensureActiveSession();
        return dishDao.findAll().stream().map(this::toBean).toList();
    }

    private IngredientPortion toDomainIngredientPortion(IngredientPortionBean ingredientPortionBean){
        if(ingredientPortionBean == null){
            throw new IllegalArgumentException("La porzione dell'ingrediente non può essere nulla.");
        }
        
        if(ingredientPortionBean.getIngredient() == null){
            throw new IllegalArgumentException("L'ingrediente non può essere nullo.");
        }

        String ingredientName = ingredientPortionBean.getIngredient().getName();

        Ingredient ingredient = ingredientDao.findById(ingredientName).orElseThrow(() -> new IllegalArgumentException("Ingrediente non trovato: " + ingredientName));

        double amount = ingredientPortionBean.getQuantity();
        String unitName = ingredientPortionBean.getUnit();

        Unit unit = Unit.valueOf(unitName);

        Quantity quantity = new Quantity(amount, unit);

        return new IngredientPortion(ingredient, quantity);

    }

    private List<IngredientPortion> toDomainIngredientPortions(List<IngredientPortionBean> portionBeans) {
        if(portionBeans == null || portionBeans.isEmpty()){
            throw new IllegalArgumentException("La lista delle porzioni di ingrediente non può essere vuota.");
        }
        return portionBeans.stream().map(this::toDomainIngredientPortion).toList();
    }

    public void deleteDish(String name) throws DishException{
        ensureActiveSession();
        if(name.isBlank()){
            throw new DishException("Il nome del piatto non può essere vuoto.");
        }
        
        if(dishDao.findById(name).isEmpty()){
            throw new DishException("Nessun piatto trovato con il nome: " + name);
        }

        dishDao.deleteById(name);
    }

    private DishBean toBean(Dish dish){
        DishBean dishBean = new DishBean();
        dishBean.setName(dish.getName());
        dishBean.setDescription(dish.getDescription());
        dishBean.setCourseType(dish.getCourseType());
        dishBean.setDietCategory(dish.getDietCategory());

        Money price = dish.getPrice();
        dishBean.setPrice(price.getAmount());

        Image image = dish.getImage();
        if(image != null && image.getUri() != null){
            dishBean.setImageUri(image.getUri().toString());
        } else {
            dishBean.setImageUri(null);
        }

        dishBean.setState(dish.getState());

        List<IngredientPortionBean> ingredientPortionBeans = dish.getIngredients().stream()
            .map(this::toBeanIngredientPortion).toList();

        dishBean.setIngredients(ingredientPortionBeans);

        return dishBean;
    }

    private IngredientPortionBean toBeanIngredientPortion(IngredientPortion ingredientPortion){
        IngredientPortionBean bean = new IngredientPortionBean();

        Ingredient ingredient = ingredientPortion.getIngredient();
        IngredientBean ingredientBean = IngredientMapper.toBean(ingredient);
        bean.setIngredient(ingredientBean);

        Quantity quantity = ingredientPortion.getQuantity();
        bean.setQuantity(quantity.getAmount());
        bean.setUnit(quantity.getUnit().name());

        return bean;
    }

    public void ensureActiveSession(){
        sessionManager.requireActiveSession();
    }
}
