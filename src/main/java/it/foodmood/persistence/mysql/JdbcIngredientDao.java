package it.foodmood.persistence.mysql;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.foodmood.config.JdbcConnectionManager;
import it.foodmood.domain.model.Ingredient;
import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.Macronutrients;
import it.foodmood.domain.value.Unit;
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

    private static final String CALL_SAVE_INGREDIENT = "{CALL insert_ingredient(?,?,?,?,?,?)}";
    private static final String CALL_GET_INGREDIENT_BY_NAME = "{CALL get_ingredient_by_name(?)}";
    private static final String CALL_GET_ALL_INGREDIENTS = "{CALL get_all_ingredients()}";
    private static final String CALL_DELETE_INGREDIENT_BY_NAME = "{CALL delete_ingredient_by_name(?)}";

    @Override
    public void insert(Ingredient ingredient){
        try {
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            executeInsert(conn, ingredient);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private void executeInsert(Connection conn, Ingredient ingredient) throws SQLException{
        try (CallableStatement cs = conn.prepareCall(CALL_SAVE_INGREDIENT)) {

            cs.setString(1, ingredient.getName());

            Macronutrients macronutrients = ingredient.getMacro();
            cs.setBigDecimal(2, BigDecimal.valueOf(macronutrients.getProtein()));
            cs.setBigDecimal(3, BigDecimal.valueOf(macronutrients.getCarbohydrates()));
            cs.setBigDecimal(4, BigDecimal.valueOf(macronutrients.getFat()));

            cs.setString(5, ingredient.getUnit().name());

            Set<Allergen> allergens = ingredient.getAllergens();
            if(allergens == null || allergens.isEmpty()){
                cs.setString(6, "[]"); // array vuoto
            } else {
                String json = allergens.stream().map(a -> "\"" + a.name() + "\"").collect(Collectors.joining(",", "[", "]"));
                cs.setString(6, json);
            }
            
            cs.execute();
        }
    }

    @Override
    public Optional<Ingredient> findById(String name){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            return executeFindById(conn, name);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Optional<Ingredient> executeFindById(Connection conn, String name) throws SQLException{
        try (CallableStatement cs = conn.prepareCall(CALL_GET_INGREDIENT_BY_NAME)){
            cs.setString(1, name);
            try(ResultSet rs = cs.executeQuery()){
                return mapIngredient(name, rs);
            }
        }
    }

    private Optional<Ingredient> mapIngredient(String name, ResultSet rs) throws SQLException{
        if(!rs.next()){
            return Optional.empty();
        }

        double protein = rs.getDouble("protein");
        double carbohydrate = rs.getDouble("carbohydrate");
        double fat = rs.getDouble("fat");
        String unitStr = rs.getString("unit");

        Macronutrients macronutrients = new Macronutrients(protein, carbohydrate, fat);
        Set<Allergen> allergens = new HashSet<>();

        Unit unit;
        try {
            unit = Unit.valueOf(unitStr); 
        } catch (Exception _) {
            throw new PersistenceException("Unità di misura non valida: " + unitStr);
        }

        do{
            String allergenType = rs.getString("allergen_type");
            if(allergenType != null){
                allergens.add(Allergen.valueOf(allergenType));
            } 
        } while (rs.next());

        Ingredient ingredient = new Ingredient(name, macronutrients, unit, allergens);
        return Optional.of(ingredient);
    }

    @Override
    public List<Ingredient> findAll(){
        try{
            Connection conn = JdbcConnectionManager.getInstance().getConnection();
            return executeFindAll(conn);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private List<Ingredient> executeFindAll(Connection conn) throws SQLException{
        try (CallableStatement cs = conn.prepareCall(CALL_GET_ALL_INGREDIENTS)) {
            ResultSet rs = cs.executeQuery();

            Map<String, Macronutrients> macroMap = new HashMap<>();
            Map<String, Set<Allergen>> allergenMap = new HashMap<>();
            Map<String, Unit> unitMap = new HashMap<>();


            while (rs.next()) {
                String name = rs.getString("name");
                double protein = rs.getDouble("protein");
                double carbohydrate = rs.getDouble("carbohydrate");
                double fat = rs.getDouble("fat");
                String unitStr = rs.getString("unit");
                String allergenType = rs.getString("allergen_type");

                if(!macroMap.containsKey(name)){
                    Macronutrients macronutrients = new Macronutrients(protein, carbohydrate, fat);
                    macroMap.put(name, macronutrients);
                }

                if(!unitMap.containsKey(name)){
                    try {
                        Unit unit = Unit.valueOf(unitStr);
                        unitMap.put(name, unit);
                    } catch (Exception _) {
                        throw new PersistenceException("Unità di misura non valida");
                    }
                }

                if(allergenType != null){
                    Set<Allergen> set = allergenMap.get(name);
                    if(set == null) {
                        set = new HashSet<>();
                        allergenMap.put(name, set);
                    }
                    set.add(Allergen.valueOf(allergenType));
                }
            }

            List<Ingredient> result = new ArrayList<>();

            for(Map.Entry<String, Macronutrients> entry : macroMap.entrySet()){
                String ingredientName = entry.getKey();
                Macronutrients macro = entry.getValue();
                Unit unit = unitMap.get(ingredientName);
                Set<Allergen> allergens = allergenMap.get(ingredientName);
                result.add(new Ingredient(ingredientName, macro, unit, allergens));
            }

            return result;
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
