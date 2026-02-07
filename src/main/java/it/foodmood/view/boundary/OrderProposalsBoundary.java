package it.foodmood.view.boundary;

import it.foodmood.bean.AnswerBean;
import it.foodmood.bean.ResponseBean;
import it.foodmood.controller.OrderProposalsController;
import it.foodmood.exception.OrderException;

public class OrderProposalsBoundary {
    
    private final OrderProposalsController controller;
    
    public OrderProposalsBoundary(){
        this.controller = new OrderProposalsController();
    }

    public ResponseBean start() throws OrderException{
        return controller.start();
    }

    public ResponseBean submit(AnswerBean answer) throws OrderException{
        return controller.submit(answer);
    }
}
