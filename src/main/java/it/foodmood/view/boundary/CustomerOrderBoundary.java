package it.foodmood.view.boundary;

import it.foodmood.controller.CustomerOrderController;
import it.foodmood.exception.OrderException;

public class CustomerOrderBoundary {
    private final CustomerOrderController controller;
    
    public CustomerOrderBoundary(){
        this.controller = new CustomerOrderController();
    }

    public String confirmOrder(String tableSessionId) throws OrderException{
        return controller.createOrder(tableSessionId);
    }
}
