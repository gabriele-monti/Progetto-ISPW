package it.foodmood.persistence.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
import it.foodmood.domain.value.Quantity;
import it.foodmood.domain.value.Unit;
import it.foodmood.exception.PersistenceException;
import it.foodmood.persistence.dao.DaoFactory;
import it.foodmood.persistence.dao.DishDao;
import it.foodmood.persistence.dao.IngredientDao;

public class JdbcDishDao implements DishDao {
        
    private static final String CALL_INSERT_DISH = "{CALL insert_dish(?,?,?,?,?,?,?,?,?)}";
    private static final String CALL_GET_DISH_BY_NAME = "{CALL get_dish_by_name(?)}";
    private static final String CALL_GET_DISH_BY_ID = "{CALL get_dish_by_id(?)}";
    private static final String CALL_GET_ALL_DISHES = "{CALL get_all_dishes()}";
    private static final String CALL_GET_DISHES_BY_COURSE = "{CALL get_dishes_by_course_type(?)}";
    private static final String CALL_GET_DISHES_BY_DIET_CATEGORY = "{CALL get_dishes_by_diet_category(?)}";
    private static final String CALL_GET_DISH_INGREDIENTS = "{CALL get_dish_ingredients(?)}";
    private static final String CALL_DELETE_DISH_BY_ID = "{CALL delete_dish_by_id(?)}";

    // Unica istanza di dao del Dish che usa jdbc
    private static JdbcDishDao instance;
    private final IngredientDao ingredientDao;

    public static synchronized JdbcDishDao getInstance(){
        if(instance == null){
            instance = new JdbcDishDao();
        }
        return instance;
    }

    private JdbcDishDao(){
        this.ingredientDao = DaoFactory.getInstance().getIngredientDao();
    }

    @Override
    public void insert(Dish dish){
        try {
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_INSERT_DISH)){
                cs.setString(1, dish.getId().toString());
                cs.setString(2, dish.getName());
                cs.setString(3, dish.getDescription());
                cs.setString(4, dish.getCourseType().name());
                cs.setString(5, dish.getDietCategory().name());
                cs.setBigDecimal(6, dish.getPrice().getAmount());

                if(dish.getImage() != null && dish.getImage().getUri() != null){
                    cs.setString(7, dish.getImage().getUri().toString());
                } else {
                    cs.setNull(7, Types.VARCHAR);
                }

                cs.setString(8, dish.getState().name());

                String ingredientsJson = toIngredientJson(dish.getIngredients());
                cs.setString(9, ingredientsJson);
                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<Dish> findByName(String name){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            return executeFindByName(conn, name);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<Dish> findById(UUID id){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            return executeFindById(conn, id);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Optional<Dish> executeFindByName(Connection conn, String name) throws SQLException{
        try (CallableStatement cs = conn.prepareCall(CALL_GET_DISH_BY_NAME)){
            cs.setString(1, name);
            try(ResultSet rs = cs.executeQuery()){
                if(!rs.next()) return Optional.empty();

                Dish dish = mapRowToDish(rs);
                dish.setIngredients(loadIngredientsForDish(conn, dish.getId()));

                return Optional.of(dish);
            }
        }
    }

    private Optional<Dish> executeFindById(Connection conn, UUID id) throws SQLException{
        try (CallableStatement cs = conn.prepareCall(CALL_GET_DISH_BY_ID)){
            cs.setString(1, id.toString());
            try(ResultSet rs = cs.executeQuery()){
                if(rs.next()){
                    Dish dish = mapRowToDish(rs);
                    List<IngredientPortion> ingredients = loadIngredientsForDish(conn, id);
                    dish.setIngredients(ingredients);

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

                    List<IngredientPortion> ingredients = loadIngredientsForDish(conn, dish.getId());
                    dish.setIngredients(ingredients);

                    dishes.add(dish);
                }
                return dishes;
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteById(UUID id){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_DELETE_DISH_BY_ID)){
                cs.setString(1, id.toString());
                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findByCourseType(CourseType courseType){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_DISHES_BY_COURSE)){
                cs.setString(1, courseType.name());
                try(ResultSet rs = cs.executeQuery()){
                    List<Dish> dishes = new ArrayList<>();
                    while(rs.next()){
                        Dish dish = mapRowToDish(rs);
                        dish.setIngredients((loadIngredientsForDish(conn, dish.getId())));
                        dishes.add(dish);
                    }
                    return dishes;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Dish> findByDietCategory(DietCategory dietCategory){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_DISHES_BY_DIET_CATEGORY)){
                cs.setString(1, dietCategory.name());
                try(ResultSet rs = cs.executeQuery()){
                    List<Dish> dishes = new ArrayList<>();
                    while(rs.next()){
                        Dish dish = mapRowToDish(rs);
                        dish.setIngredients((loadIngredientsForDish(conn, dish.getId())));
                        dishes.add(dish);
                    }
                    return dishes;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private List<IngredientPortion> loadIngredientsForDish(Connection conn, UUID dishId) throws SQLException{
        List<IngredientPortion> portions = new ArrayList<>();

        try(CallableStatement cs = conn.prepareCall(CALL_GET_DISH_INGREDIENTS)){
            cs.setString(1, dishId.toString());

            try(ResultSet rs = cs.executeQuery()){
                while (rs.next()) {
                    String ingredientName = rs.getString("ingredient_name");
                    BigDecimal quantityValue = rs.getBigDecimal("quantity");
                    String unitStr = rs.getString("unit");
                    
                    var ingredientDb = ingredientDao.findById(ingredientName);
                    if(ingredientDb.isEmpty()){
                        throw new PersistenceException("Ingrediente non trovato: " + ingredientName);
                    }

                    var ingredient = ingredientDb.get();

                    double amount = quantityValue.doubleValue();
                    Unit unit = Unit.valueOf(unitStr);
                    Quantity quantity = new Quantity(amount, unit);

                    portions.add(new IngredientPortion(ingredient, quantity));
                }
            }
        }

        return portions;
    }

    private Dish mapRowToDish(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id_dish"));
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

        return Dish.fromPersistence(id, name, description, courseType, dietCategory, ingredients, state, image, price);
    }

    private String toIngredientJson(List<IngredientPortion> portions){
        StringBuilder stringBuilder = new StringBuilder("[");
        for(int i = 0; i < portions.size(); i++){
            IngredientPortion p = portions.get(i);
            if(i > 0) stringBuilder.append(",");

            stringBuilder.append("{").append("\"ingredientName\":\"").append(p.getIngredient().getName()).append("\",")
                         .append("\"quantity\":").append(p.getQuantity().getAmount()).append(",")
                         .append("\"unit\":\"").append(p.getQuantity().getUnit().name()).append("\"").append("}");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
