package it.foodmood.persistence.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import java.sql.CallableStatement;

import it.foodmood.domain.model.Dish;
import it.foodmood.persistence.DriverManagerConnection;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.persistence.exception.PersistenceException;

public class JdbcDishDao implements DishDao {
        
    private static final String CALL_SAVE_DISH = "{CALL save_dish(?,?,?,?,?,?,?)}";
    private static final String CALL_GET_DISH_BY_ID = "{CALL get_dish_by_id(?)}";
    private static final String CALL_GET_ALL_DISHES = "{CALL get_all_dishes()}";
    private static final String CALL_GET_DISHES_BY_COURSE = "{CALL get_dishes_by_course_type(?)}";
    private static final String CALL_GET_DISHES_BY_DIET = "{CALL get_dishes_by_diet(?)}";
    private static final String CALL_DELETE_DISH_BY_ID = "{CALL delete_dish_by_id(?)}";

    // Unica istanza di dao del piatto che usa jdbc
    private static JdbcDishDao instance = null;

    public static synchronized JdbcDishDao getInstance(){
        if(instance == null){
            instance = new JdbcDishDao();
        }
        return instance;
    }

    @Override
    public void save(Dish dish){
        try (Connection conn = DriverManagerConnection.getInstance().getConnection();
            CallableStatement cs = conn.prepareCall(CALL_SAVE_DISH)){
                bindDish(cs,dish);
                cs.execute();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<Dish> findById(String id){
        try (Connection conn = DriverManagerConnection.getInstance().getConnection();
            CallableStatement cs = conn.prepareCall(CALL_GET_DISH_BY_ID)){
                cs.setString(1, id);
                // Da continuare
            return Optional.empty();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findAll(){
        try (Connection conn = DriverManagerConnection.getInstance().getConnection();
            CallableStatement cs = conn.prepareCall(CALL_GET_ALL_DISHES);
                ResultSet rs = cs.executeQuery()){
                    // List<Dish> out = new ArrayList<>();
                    while (rs.next()) {
                        // Da implementare
                    }
            return List.of();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteById(String id){
        try (Connection conn = DriverManagerConnection.getInstance().getConnection();
            CallableStatement cs = conn.prepareCall(CALL_DELETE_DISH_BY_ID)){
                cs.setString(1, id);
                cs.execute();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findByCategory(String category){
        try (Connection conn = DriverManagerConnection.getInstance().getConnection();
            CallableStatement cs = conn.prepareCall(CALL_GET_DISHES_BY_COURSE)){
            cs.setString(1, category);
            return List.of();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findByDietCategory(String dietCategory){
        try (Connection conn = DriverManagerConnection.getInstance().getConnection();
            CallableStatement cs = conn.prepareCall(CALL_GET_DISHES_BY_DIET)){
            cs.setString(1, dietCategory);
            return List.of();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    // Associazione dei campi dell'entita Piatto ai parametri della store procedures
    private void bindDish(CallableStatement cs, Dish dish) throws SQLException{
        cs.setString(1, dish.getId());
        cs.setString(2, dish.getName());
        cs.setString(3, dish.getDescription().orElse(null));
        cs.setString(4, dish.getCourseType().name());
        cs.setString(5, dish.getDietCategory().name());
        cs.setBigDecimal(6, dish.getPrice().amount());
        cs.setString(7, dish.getImage().map(img -> img.uri().toString()).orElse(null));
    }
}
