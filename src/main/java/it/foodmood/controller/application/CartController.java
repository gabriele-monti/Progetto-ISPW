package it.foodmood.controller.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import it.foodmood.bean.OrderLineBean;
import it.foodmood.domain.model.Cart;
import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.OrderLine;
import it.foodmood.exception.CartException;
import it.foodmood.exception.PersistenceException;
import it.foodmood.exception.SessionExpiredException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.utils.SessionManager;

public class CartController {
    private final Cart cart;
    private final DishDao dishDao;
    private final SessionManager sessionManager;

    public CartController(){
        this.cart = new Cart();
        this.dishDao = DaoFactory.getInstance().getDishDao();
        this.sessionManager = SessionManager.getInstance();
    }

    public void addToCart(String dishId, int quantity) throws CartException{
        ensureActiveSession();

        if(dishId == null || dishId.isBlank()){
            throw new CartException("ID articolo non valido");
        }

        if(quantity <= 0){
            throw new CartException("Quantità non valida");
        }

        try {
            UUID id = UUID.fromString(dishId);

            Dish dish = dishDao.findById(id).orElseThrow(() -> new CartException("Articolo non disponibile"));

            cart.addLine(dish.getId(), dish.getName(), dish.getPrice(), quantity);
        } catch (IllegalArgumentException _){
            throw new CartException("ID articolo non valido");
        } catch (PersistenceException e) {
            throw new CartException("Errore tecnico: impossibile aggiungere l'articolo al carrello. Riprova più tardi", e);
        }
    }

    public List<OrderLineBean> getCartItems() throws CartException{
        ensureActiveSession();

        return cart.getLines().stream().map(this::toLineBean).toList();
    }


    public void removeFromCart(String dishId) throws CartException{
        ensureActiveSession();

        if(dishId == null || dishId.isBlank()){
            throw new CartException("ID articolo non valido");
        }

        try {
            UUID id = UUID.fromString(dishId);
            cart.removeLine(id);
        } catch (IllegalArgumentException _) {
            throw new CartException("Errore tecnico: impossibile rimuovere l'articolo dal carrello.");
        }
    }


    public void clearCart(){
        cart.clear();
    }

    public BigDecimal getTotal(){
        return cart.getTotal();
    }

    private OrderLineBean toLineBean(OrderLine orderLine){
        OrderLineBean orderLineBean = new OrderLineBean();
        orderLineBean.setDishId(orderLine.getDishId().toString());
        orderLineBean.setProductName(orderLine.getProductName());
        orderLineBean.setUnitPrice(orderLine.getUnitPrice().getAmount());
        orderLineBean.setQuantity(orderLine.getQuantity());
        return orderLineBean;
    }

    public void ensureActiveSession() throws CartException{
        try {
            sessionManager.requireActiveSession();
        } catch (SessionExpiredException e) {
            throw new CartException(e.getMessage(), e);
        }
    }
}
