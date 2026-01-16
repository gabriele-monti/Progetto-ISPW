package it.foodmood.view.boundary;

import java.math.BigDecimal;
import java.util.List;

import it.foodmood.bean.OrderLineBean;
import it.foodmood.controller.application.CartController;
import it.foodmood.exception.CartException;

public class CartBoundary {
    private final CartController controller;

    public CartBoundary(){
        this.controller = new CartController();
    }

    public void addProduct(String dishId, int quantity) throws CartException{
        controller.addToCart(dishId, quantity);
    }

    public List<OrderLineBean> getCartItems() throws CartException{
        return controller.getCartItems();
    }

    public void removeItem(String dishId) throws CartException{
        controller.removeFromCart(dishId);
    }

    public BigDecimal getTotal(){
        return controller.getTotal();
    }

    public void clearCart(){
        controller.clearCart();
    }
}
