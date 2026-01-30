package it.foodmood.view.boundary;

import java.util.List;

import it.foodmood.bean.DishBean;
import it.foodmood.controller.MenuController;
import it.foodmood.domain.value.CourseType;
import it.foodmood.exception.DishException;

public class MenuBoundary {
 
    private final MenuController controller;

    public MenuBoundary(){
        this.controller = new MenuController();
    }

    public List<DishBean> getDishes() throws DishException{
        return controller.loadAllDishes();
    }

    public List<DishBean> filterDishesByCourseType(CourseType courseType) throws DishException{
        return controller.loadDishesByCourseType(courseType);
    }

}
