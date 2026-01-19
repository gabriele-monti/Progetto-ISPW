package it.foodmood.view.boundary;

import java.util.List;

import it.foodmood.bean.DishBean;
import it.foodmood.controller.DishController;
import it.foodmood.domain.value.CourseType;
import it.foodmood.exception.DishException;

public class DishBoundary {
    
    private final DishController controller;

    public DishBoundary(){
        this.controller = new DishController();
    }

    public void createDish(DishBean dishBean) throws DishException{
        controller.createDish(dishBean);
    }

    public List<DishBean> getAllDishes() throws DishException{
        return controller.getAllDishes();
    }

    public List<DishBean> getDishesByCourseType(CourseType courseType) throws DishException{
        return controller.getDishesByCourseType(courseType);
    }

    public void deleteDish(String name) throws DishException{
        controller.deleteDish(name);
    }
}

