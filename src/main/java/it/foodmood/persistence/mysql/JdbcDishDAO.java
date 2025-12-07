package it.foodmood.persistence.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.CallableStatement;

import it.foodmood.config.JdbcConnectionManager;
import it.foodmood.domain.model.Dish;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.DishState;
import it.foodmood.domain.value.Image;
import it.foodmood.domain.value.IngredientPortion;
import it.foodmood.domain.value.Money;
import it.foodmood.exception.PersistenceException;
import it.foodmood.persistence.dao.DishDao;

public class JdbcDishDao implements DishDao {
        
    private static final String CALL_SAVE_DISH = "{CALL insert_dish(?,?,?,?,?,?,?)}";
    private static final String CALL_GET_DISH_BY_NAME = "{CALL get_dish_by_name(?)}";
    private static final String CALL_GET_ALL_DISHES = "{CALL get_all_dishes()}";
    private static final String CALL_GET_DISHES_BY_COURSE = "{CALL get_dishes_by_course_type(?)}";
    private static final String CALL_GET_DISHES_BY_DIET = "{CALL get_dishes_by_diet(?)}";
    private static final String CALL_DELETE_DISH_BY_NAME = "{CALL delete_dish_by_name(?)}";

    // Unica istanza di dao del Dish che usa jdbc
    private static JdbcDishDao instance;

    public static synchronized JdbcDishDao getInstance(){
        if(instance == null){
            instance = new JdbcDishDao();
        }
        return instance;
    }

    private JdbcDishDao(){
        // costruttore privato
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
    public Optional<Dish> findById(String name){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            return executeFindById(conn, name);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Optional<Dish> executeFindById(Connection conn, String name) throws SQLException{
        try (CallableStatement cs = conn.prepareCall(CALL_GET_DISH_BY_NAME)){
            cs.setString(1, name);
            try(ResultSet rs = cs.executeQuery()){
                if(rs.next()){
                    Dish dish = mapRowToDish(rs);
                    return Optional.of(dish);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public List<Dish> findAll(){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_ALL_DISHES)) {
                ResultSet rs = cs.executeQuery();

                List<Dish> dishes = new ArrayList<>();

                while (rs.next()) {
                    Dish dish = mapRowToDish(rs);
                    dishes.add(dish);
                }
                return dishes;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteById(String name){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_DELETE_DISH_BY_NAME)){
                cs.setString(1, name);
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

    private Dish mapRowToDish(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String description = rs.getString("description");
        String courseTypeStr = rs.getString("course_type");
        CourseType courseType = CourseType.valueOf(courseTypeStr);
        String dietCategoryStr = rs.getString("diet_category");
        DietCategory dietCategory = DietCategory.valueOf(dietCategoryStr);
        BigDecimal priceValue = rs.getBigDecimal("price");
        Money price = new Money(priceValue);

        String imageUri = rs.getString("image_uri");
        Image image = null;
        if(imageUri != null && !imageUri.isBlank()){
            image = new Image(URI.create(imageUri));
        }

        String stateStr = rs.getString("state");
        DishState state = DishState.valueOf(stateStr);

        var ingredients = new ArrayList<IngredientPortion>();

        return new Dish(name, description, courseType, dietCategory, ingredients, state, image, price);
    }
}
