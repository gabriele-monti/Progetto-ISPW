package it.foodmood.view.boundary;

import java.math.BigDecimal;
import java.util.List;

import it.foodmood.bean.CartItemBean;
import it.foodmood.controller.CartController;
import it.foodmood.exception.CartException;

public class CartBoundary {
    private final CartController controller;

    public CartBoundary(){
        this.controller = new CartController();
    }

    public void addProduct(String dishId, int quantity) throws CartException{
        controller.addToCart(dishId, quantity);
    }

    public List<CartItemBean> getCartItems() throws CartException{
        return controller.getCartItems();
    }

    public void removeItem(String dishId) throws CartException{
        controller.removeFromCart(dishId);
    }

    public BigDecimal getTotal() throws CartException{
        return controller.getTotal();
    }
}
