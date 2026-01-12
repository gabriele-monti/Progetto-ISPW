package it.foodmood.controller.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import it.foodmood.bean.OrderLineBean;
import it.foodmood.domain.model.Cart;
import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.OrderLine;
import it.foodmood.exception.CartException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.utils.SessionManager;

public class CartController {
    private Cart cart;
    private final DishDao dishDao;
    private final SessionManager sessionManager;

    public CartController(){
        this.cart = new Cart();
        this.dishDao = DaoFactory.getInstance().getDishDao();
        this.sessionManager = SessionManager.getInstance();
    }

    public void addToCart(String dishId, int quantity) throws CartException{
        ensureActiveSession();

        UUID id = UUID.fromString(dishId);

        Dish dish = dishDao.findById(id).orElseThrow(() -> new CartException("Articolo non disponibile"));

        cart.addLine(dish.getId(), dish.getName(), dish.getPrice(), quantity);
    }

    public List<OrderLineBean> getCartItems(){
        ensureActiveSession();
        return cart.getLines().stream().map(this::toLineBean).toList();
    }

    public void clearCart(){
        ensureActiveSession();
        cart.clear();
    }

    public List<OrderLine> getOrderLines() throws CartException{
        ensureActiveSession();
        if(cart.isEmpty()){
            throw new CartException("Il carrello è vuoto");
        }
        return cart.getLines();
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

    public void ensureActiveSession(){
        sessionManager.requireActiveSession();
    }
}
