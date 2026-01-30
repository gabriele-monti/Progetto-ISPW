package it.foodmood.controller;

import java.util.List;

import it.foodmood.bean.DishBean;
import it.foodmood.controller.mapper.DishMapper;
import it.foodmood.domain.value.CourseType;
import it.foodmood.exception.DishException;
import it.foodmood.exception.PersistenceException;
import it.foodmood.exception.SessionExpiredException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.utils.SessionManager;

public class MenuController {

    private final DishDao dishDao;
    private final SessionManager sessionManager = SessionManager.getInstance();
    private final DishMapper dishMapper;


    public MenuController(){
        DaoFactory factory = DaoFactory.getInstance();
        this.dishDao = factory.getDishDao();
        this.dishMapper = new DishMapper();
    }


    public List<DishBean> loadAllDishes() throws DishException{
        ensureActiveSession();
        try {
            return dishMapper.toBeans(dishDao.findAll());
        } catch (PersistenceException e) {
            throw new DishException("Spiacenti si è verificato un errore tecnico durante il recupero dei piatti, riprovare in seguito.", e);
        }
    }

    public List<DishBean> loadDishesByCourseType(CourseType courseType) throws DishException{
        ensureActiveSession();
        try {
            return dishMapper.toBeans(dishDao.findByCourseType(courseType));
        } catch (PersistenceException e) {
            throw new DishException("Spiacenti si è verificato un errore tecnico, riprovare in seguito.", e);
        }
    }

    private void ensureActiveSession() throws DishException{
        try {
            sessionManager.requireActiveSession();
        } catch (SessionExpiredException e) {
            throw new DishException(e.getMessage());
        }
    }
}
