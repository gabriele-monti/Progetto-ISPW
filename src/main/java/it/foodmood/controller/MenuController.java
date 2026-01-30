package it.foodmood.controller;

import java.util.List;
import java.util.UUID;

import it.foodmood.bean.DishBean;
import it.foodmood.bean.IngredientBean;
import it.foodmood.bean.IngredientPortionBean;
import it.foodmood.controller.mapper.IngredientMapper;
import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.Ingredient;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.Image;
import it.foodmood.domain.value.IngredientPortion;
import it.foodmood.domain.value.Money;
import it.foodmood.domain.value.Quantity;
import it.foodmood.exception.DishException;
import it.foodmood.exception.PersistenceException;
import it.foodmood.exception.SessionExpiredException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.utils.SessionManager;

public class MenuController {

    private final DishDao dishDao;
    private final SessionManager sessionManager = SessionManager.getInstance();

    public MenuController(){
        DaoFactory factory = DaoFactory.getInstance();
        this.dishDao = factory.getDishDao();
    }


    public List<DishBean> getAllDishes() throws DishException{
        ensureActiveSession();
        try {
            return dishDao.findAll().stream().map(this::toBean).toList();
        } catch (PersistenceException e) {
            throw new DishException("Spiacenti si è verificato un errore tecnico durante il recupero dei piatti, riprovare in seguito.", e);
        }
    }

    public List<DishBean> getDishesByCourseType(CourseType courseType) throws DishException{
        ensureActiveSession();
        try {
            return dishDao.findByCourseType(courseType).stream().map(this::toBean).toList();
        } catch (PersistenceException e) {
            throw new DishException("Spiacenti si è verificato un errore tecnico, riprovare in seguito.", e);
        }
    }

    public void deleteDish(String id) throws DishException{
        ensureActiveSession();
        if(id == null || id.isBlank()){
            throw new DishException("L'id del piatto non può essere vuoto.");
        }
        
        try {
            UUID dishId = UUID.fromString(id);

            if(dishDao.findById(dishId).isEmpty()){
                throw new DishException("Nessun piatto trovato con id: " + id);
            }

            dishDao.deleteById(dishId);

        } catch (IllegalArgumentException e) {
            throw new DishException("Formato id non valido: " + id, e);
        } catch (PersistenceException _){
            throw new DishException("Errore tecnico durante l'eliminazione del piatto. Riprova più tardi.");
        }
    }

    private DishBean toBean(Dish dish){
        DishBean dishBean = new DishBean();
        dishBean.setId(dish.getId().toString());
        dishBean.setName(dish.getName());
        dishBean.setDescription(dish.getDescription());
        dishBean.setCourseType(dish.getCourseType());
        dishBean.setDietCategories(dish.getDietCategories());

        Money price = dish.getPrice();
        dishBean.setPrice(price.getAmount());

        dishBean.setKcal(dish.getKcal());

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

        Ingredient ingredient = ingredientPortion.ingredient();
        IngredientBean ingredientBean = IngredientMapper.toBean(ingredient);
        bean.setIngredient(ingredientBean);

        Quantity quantity = ingredientPortion.quantity();
        bean.setQuantity(quantity.amount());
        bean.setUnit(quantity.unit().name());

        return bean;
    }

    private void ensureActiveSession() throws DishException{
        try {
            sessionManager.requireActiveSession();
        } catch (SessionExpiredException e) {
            throw new DishException(e.getMessage());
        }
    }
}
