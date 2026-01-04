package it.foodmood.view.ui.gui;

import java.math.BigDecimal;

import it.foodmood.bean.DishBean;
import it.foodmood.bean.OrderLineBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cart {
    
    private final ObservableList<OrderLineBean> items = FXCollections.observableArrayList();

    public ObservableList<OrderLineBean> getItems(){
        return items;
    }

    public void addItem(DishBean dishBean, int quantity){

        for(int i = 0; i < items.size(); i++){
            OrderLineBean line = items.get(i);
            if(line.getDishId().equals(dishBean.getId())){
                line.setQuantity(line.getQuantity() + quantity);
                items.set(i, line);
                return;
            }
        }

        OrderLineBean line = new OrderLineBean();
        line.setDishId(dishBean.getId());
        line.setProductName(dishBean.getName());
        line.setUnitPrice(dishBean.getPrice());
        line.setQuantity(quantity);
        items.add(line);
    }

    public void removeItem(String dishId){
        items.removeIf(line -> dishId.equals(line.getDishId()));
    }

    public BigDecimal total(){
        BigDecimal total = BigDecimal.ZERO;
        for(OrderLineBean line : items){
            BigDecimal subtotal = line.getSubTotal();
            total = total.add(subtotal);
        }
        return total;
    }

    public void clear(){
        items.clear();
    }
}
