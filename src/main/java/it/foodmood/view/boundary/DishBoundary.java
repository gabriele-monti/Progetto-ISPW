package it.foodmood.view.boundary;

import java.util.List;

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

    public List<DishBean> getAllDishes(){
        return controller.getAllDishes();
    }

    public void deleteDish(String name) throws DishException{
        controller.deleteDish(name);
    }

    public void ensureActiveSession(){
        controller.ensureActiveSession();
    }
}

