package it.foodmood.persistence.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import java.sql.CallableStatement;

import it.foodmood.config.JdbcConnectionManager;
import it.foodmood.domain.model.Dish;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.persistence.exception.PersistenceException;

public class JdbcDishDao implements DishDao {
        
    private static final String CALL_SAVE_DISH = "{CALL insert_dish(?,?,?,?,?,?,?)}";
    private static final String CALL_GET_DISH_BY_ID = "{CALL get_dish_by_id(?)}";
    private static final String CALL_GET_ALL_DISHES = "{CALL get_all_dishes()}";
    private static final String CALL_GET_DISHES_BY_COURSE = "{CALL get_dishes_by_course_type(?)}";
    private static final String CALL_GET_DISHES_BY_DIET = "{CALL get_dishes_by_diet(?)}";
    private static final String CALL_DELETE_DISH_BY_ID = "{CALL delete_dish_by_id(?)}";

    // Unica istanza di dao del Dish che usa jdbc
    private static JdbcDishDao instance;

    public static synchronized JdbcDishDao getInstance(){
        if(instance == null){
            instance = new JdbcDishDao();
        }
        return instance;
    }

    @Override
    public void insert(Dish dish){
        try {
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_SAVE_DISH)){
                cs.setString(1, dish.getName());
                cs.setString(2, dish.getDescription());
                cs.setString(3, dish.getCourseType().name());
                cs.setString(4, dish.getDietCategory().name());
                cs.setBigDecimal(5, dish.getPrice().getAmount());

                if(dish.getImage() != null && dish.getImage().getUri() != null){
                    cs.setString(6, dish.getImage().getUri().toString());
                } else {
                    cs.setNull(6, Types.VARCHAR);
                }

                cs.setString(7, dish.getState().name());
                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<Dish> findById(String id){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_DISH_BY_ID)){
                cs.setString(1, id);
                // Da continuare
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findAll(){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_ALL_DISHES)) {
                ResultSet rs = cs.executeQuery();
                while (rs.next()) {
                    // Da implementare
                }
                return List.of();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteById(String id){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_DELETE_DISH_BY_ID)){
                cs.setString(1, id);
                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findByCategory(String category){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_DISHES_BY_COURSE)){
                cs.setString(1, category);
                return List.of();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findByDietCategory(String dietCategory){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_DISHES_BY_DIET)){
                cs.setString(1, dietCategory);
                return List.of();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
