package it.foodmood.persistence.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import it.foodmood.config.JdbcConnectionManager;
import it.foodmood.domain.model.Ingredient;
import it.foodmood.persistence.dao.IngredientDao;
import it.foodmood.persistence.exception.PersistenceException;

public class JdbcIngredientDao implements IngredientDao {

    private static JdbcIngredientDao instance;

    public static synchronized JdbcIngredientDao getInstance(){
        if(instance == null){
            instance = new JdbcIngredientDao();
        }
        return instance;
    }

    private static final String CALL_SAVE_INGREDIENT = "{CALL insert_ingredient(?,?,?,?,?,?,?)}";
    private static final String CALL_GET_INGREDIENT_BY_NAME = "{CALL get_ingredient_by_name(?)}";
    private static final String CALL_GET_ALL_INGREDIENTS = "{CALL get_all_ingredients()}";
    private static final String CALL_DELETE_INGREDIENT_BY_NAME = "{CALL delete_ingredient_by_name(?)}";


    @Override
    public void insert(Ingredient ingredient){
        try {
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_SAVE_INGREDIENT)){
                cs.setString(1, ingredient.getName());
                // cs.setString(2, ingredient.getMacro());
                // cs.setString(3, .getDescription().orElse(null));
                // cs.setString(4, .getCourseType().name());

                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Optional<Ingredient> findById(String name){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_INGREDIENT_BY_NAME)){
                cs.setString(1, name);
                // Da continuare
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Ingredient> findAll(){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            try (CallableStatement cs = conn.prepareCall(CALL_GET_ALL_INGREDIENTS)) {
                ResultSet rs = cs.executeQuery();
                // List<Dish> out = new ArrayList<>();
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
            try (CallableStatement cs = conn.prepareCall(CALL_DELETE_INGREDIENT_BY_NAME)){
                cs.setString(1, id);
                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
