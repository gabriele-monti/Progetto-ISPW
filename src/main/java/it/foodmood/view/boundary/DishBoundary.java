package it.foodmood.view.boundary;

import it.foodmood.bean.DishBean;
import it.foodmood.controller.application.DishController;
import it.foodmood.exception.DishException;

public class DishBoundary {
    
    private final DishController controller;

    public DishBoundary(){
        this.controller = new DishController();
    }

    public void createDish(DishBean dishBean) throws DishException{
        controller.createDish(dishBean);
    }

    public void ensureActiveSession(){
        controller.ensureActiveSession();
    }
}

