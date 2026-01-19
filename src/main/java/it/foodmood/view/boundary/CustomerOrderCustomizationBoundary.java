package it.foodmood.view.boundary;

import it.foodmood.bean.AnswerBean;
import it.foodmood.bean.ResponseBean;
import it.foodmood.controller.application.OrderCustomizationController;
import it.foodmood.exception.OrderException;

public class CustomerOrderCustomizationBoundary {
    
    private final OrderCustomizationController controller;
    
    public CustomerOrderCustomizationBoundary(){
        this.controller = new OrderCustomizationController();
    }

    public ResponseBean start() throws OrderException{
        return controller.start();
    }

    public ResponseBean submit(AnswerBean answer) throws OrderException{
        return controller.submit(answer);
    }
}
