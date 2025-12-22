package it.foodmood.bean;

import it.foodmood.domain.value.Money;

public class OrderLineBean {
    private final String productName;
    private final Money price;
    private final int quantity;

    public OrderLineBean(String productName, Money price, int quantity){
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName(){
        return productName;
    }

    public Money getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }
}
