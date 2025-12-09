package it.foodmood.persistence.filesystem;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.foodmood.domain.model.Dish;
import it.foodmood.domain.model.Ingredient;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.DishState;
import it.foodmood.domain.value.Image;
import it.foodmood.domain.value.IngredientPortion;
import it.foodmood.domain.value.Money;
import it.foodmood.domain.value.Quantity;
import it.foodmood.domain.value.Unit;
import it.foodmood.exception.PersistenceException;
import it.foodmood.persistence.dao.DishDao;

public class FileSystemDishDao extends AbstractCsvDao implements DishDao {
    
    private static FileSystemDishDao instance;
    private final FileSystemIngredientDao ingredientDao = FileSystemIngredientDao.getInstance();

    private static final String SEPARATOR = ";";
    private static final String INGREDIENT_SEPARATOR = "\\|";
    private static final String INGREDIENT_JOIN = "|";
    private static final String PORTION_SEPARATOR = ",";

    private FileSystemDishDao(){
        super(FileSystemPaths.DISHES);
    }

    public static synchronized FileSystemDishDao getInstance(){
        if(instance == null){
            instance = new FileSystemDishDao();
        }
        return instance;
    }

    @Override
    public void insert(Dish dish){
        String line = toCsv(dish);
        appendLine(line);
    }

    @Override
    public List<Dish> findAll(){
        List<String> lines = readAllLines();
        List<Dish> dishes = new ArrayList<>();
        for(String line: lines){
            dishes.add(fromCsv(line));
        }
        return dishes;
    }

    @Override
    public Optional<Dish> findById(String name){
        if(name == null || name.isBlank()){
            return Optional.empty();
        }
        return findAll().stream().filter(dish -> dish.getName().equals(name)).findFirst();
    }

    @Override
    public void deleteById(String name){
        if(name == null || name.isBlank()){
            return;
        }
        List<Dish> all = findAll();
        boolean removed = all.removeIf(d -> d.getName().equals(name));
        if(!removed){
            return;
        }

        List<String> lines = new ArrayList<>();
        for(Dish dish : all){
            lines.add(toCsv(dish));
        }
        overwriteAllLines(lines);
    }

    @Override
    public List<Dish> findByCategory(String category){
        if(category == null || category.isBlank()){
            return List.of();
        }

        return findAll().stream().filter(d -> d.getCourseType().name().equalsIgnoreCase(category)).toList();
    }

    @Override
    public List<Dish> findByDietCategory(String dietCategory){
        if(dietCategory == null || dietCategory.isBlank()){
            return List.of();
        }

        return findAll().stream().filter(d -> d.getDietCategory().name().equalsIgnoreCase(dietCategory)).toList();
    }

    private String toCsv(Dish dish){

        String name = dish.getName();
        String description = dish.getDescription() == null ? "" : dish.getDescription();
        String courseType = dish.getCourseType().name();
        String dietCategory = dish.getDietCategory().name();
        String imageUri = dish.getImage() == null ? "" : dish.getImage().getUri().toString();
        String state = dish.getState().name();
        String price = dish.getPrice().getAmount().toString();

        String ingredients = ingredientPortionsToString(dish.getIngredients());
        
        return name + SEPARATOR + description + SEPARATOR +
               courseType + SEPARATOR + dietCategory + SEPARATOR +
               imageUri + SEPARATOR + state + SEPARATOR + price + SEPARATOR + ingredients;
    }

    private Dish fromCsv(String line){
        String[] token = line.split(SEPARATOR, -1);

        if(token.length != 8){
            throw new PersistenceException("Riga piatto malformata: " + line);
        }

        try {        
            String name = token[0];
            String description = token[1];

            CourseType courseType = parseCourseType(token[2]);
            DietCategory dietCategory = parseDietCategory(token[3]);
            Image image = parseImage(token[4]);
            DishState state = parseDishState(token[5]);
            Money price = parseMoney(token[6]);

            List<IngredientPortion> ingredients = parseIngredientPortions(token[7]);

            return new Dish(name, description, courseType, dietCategory, ingredients, state, image, price);

        } catch (Exception e) {
            throw new PersistenceException("Errore durante il parsing della riga: " + line);
        }
    }

    private CourseType parseCourseType(String value){
        return CourseType.valueOf(value);
    }
    
    private DietCategory parseDietCategory(String value){
        return DietCategory.valueOf(value);
    }

    private DishState parseDishState(String value){
        return DishState.valueOf(value);
    }

    private Money parseMoney(String value){
        return new Money(new BigDecimal(value));
    }

    private Image parseImage(String value){
        return value.isBlank() ? null : new Image(URI.create(value));
    }

    private String ingredientPortionsToString(List<IngredientPortion> portions){
        if(portions == null || portions.isEmpty()){
            return "";
        }

        StringBuilder ingredientPortion = new StringBuilder();
        for(IngredientPortion portion : portions){
            if(!ingredientPortion.isEmpty()){
                ingredientPortion.append(INGREDIENT_JOIN);
            }
            String ingredientName = portion.getIngredient().getName();
            double quantity = portion.getQuantity().getAmount();
            String unit = portion.getQuantity().getUnit().name();

            ingredientPortion.append(ingredientName).append(PORTION_SEPARATOR).append(quantity).append(PORTION_SEPARATOR).append(unit);
        }
        return ingredientPortion.toString();
    }

    private List<IngredientPortion> parseIngredientPortions(String field){
        List<IngredientPortion> portions = new ArrayList<>();

        if(field == null || field.isBlank()){
            return portions;
        }

        String [] tokens = field.split(INGREDIENT_SEPARATOR, -1);

        for(String token: tokens){
            if (token.isBlank()) continue;

            String[] portion = token.split(PORTION_SEPARATOR, -1);
            if(portion.length != 3){
                throw new IllegalArgumentException("Porzione malformata: " + token);
            }

            String ingredientName = portion[0].trim();
            double quantity = Double.parseDouble(portion[1]);
            Unit unit = Unit.valueOf(portion[2]);

            Ingredient ingredient = ingredientDao.findById(ingredientName).orElseThrow(() -> new IllegalArgumentException("Ingrediente non trovato: " + ingredientName));

            portions.add(new IngredientPortion(ingredient, new Quantity(quantity, unit)));
        }

        return portions;
    }
}
