package it.foodmood.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import it.foodmood.bean.CartItemBean;
import it.foodmood.domain.model.Cart;
import it.foodmood.domain.model.CartItem;
import it.foodmood.domain.model.Dish;
import it.foodmood.exception.CartException;
import it.foodmood.exception.PersistenceException;
import it.foodmood.exception.SessionExpiredException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.utils.SessionManager;

public class CartController {
    private final DishDao dishDao;
    private final SessionManager sessionManager;

    private Cart cart() throws CartException{
        try {
            return sessionManager.getCart();
        } catch (SessionExpiredException e) {
            throw new CartException(e.getMessage());
        }
    }

    public CartController(){
        this.dishDao = DaoFactory.getInstance().getDishDao();
        this.sessionManager = SessionManager.getInstance();
    }

    public void addToCart(CartItemBean cartItem) throws CartException{

        String dishId = cartItem.getDishId();
        int quantity = cartItem.getQuantity();

        if(dishId == null || dishId.isBlank()){
            throw new CartException("ID articolo non valido");
        }

        if(quantity <= 0){
            throw new CartException("Quantità non valida");
        }

        try {
            UUID id = UUID.fromString(dishId);

            Dish dish = dishDao.findById(id).orElseThrow(() -> new CartException("Articolo non disponibile"));

            cart().addLine(dish.getId(), dish.getName(), dish.getPrice(), quantity);
        } catch (IllegalArgumentException _){
            throw new CartException("ID articolo non valido");
        } catch (PersistenceException e) {
            throw new CartException("Errore tecnico: impossibile aggiungere l'articolo al carrello. Riprova più tardi", e);
        }
    }

    public List<CartItemBean> getCartItems() throws CartException{
        return cart().getItems().stream().map(this::toItemBean).toList();
    }


    public void removeFromCart(String dishId) throws CartException{
        if(dishId == null || dishId.isBlank()){
            throw new CartException("ID articolo non valido");
        }

        try {
            UUID id = UUID.fromString(dishId);
            cart().removeLine(id);
        } catch (IllegalArgumentException _) {
            throw new CartException("Errore tecnico: impossibile rimuovere l'articolo dal carrello.");
        }
    }

    public BigDecimal getTotal() throws CartException{
        return cart().getTotal();
    }

    private CartItemBean toItemBean(CartItem cartItem){
        CartItemBean cartItemBean = new CartItemBean();
        cartItemBean.setDishId(cartItem.getDishId().toString());
        cartItemBean.setProductName(cartItem.getProductName());
        cartItemBean.setUnitPrice(cartItem.getUnitPrice().getAmount());
        cartItemBean.setQuantity(cartItem.getQuantity());
        return cartItemBean;
    }
}
