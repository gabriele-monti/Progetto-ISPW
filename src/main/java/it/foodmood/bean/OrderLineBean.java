package it.foodmood.bean;

import java.math.BigDecimal;

public class OrderLineBean {
    private String dishId;
    private String productName;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
    private int quantity;

    public OrderLineBean(){
        // Costruttore vuoto
    }

    public String getDishId(){
        return dishId;
    }

    public String getProductName(){
        return productName;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitPrice(){
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubTotal(){
        if(unitPrice == null) return BigDecimal.ZERO;
        subTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
