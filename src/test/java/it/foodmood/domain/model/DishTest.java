package it.foodmood.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.foodmood.domain.valueobject.Allergen;
import it.foodmood.domain.valueobject.CourseType;
import it.foodmood.domain.valueobject.DietCategory;
import it.foodmood.domain.valueobject.IngredientPortion;
import it.foodmood.domain.valueobject.Macronutrients;
import it.foodmood.domain.valueobject.Money;
import it.foodmood.domain.valueobject.Quantity;
import it.foodmood.domain.valueobject.Unit;

public class DishTest {
    @Test
    void testDish(){
        Ingredient latte = Ingredient.of("Latte", Macronutrients.of(4,5,3.5), Set.of(Allergen.MILK));
        Ingredient biscotti = Ingredient.of("Biscotti", Macronutrients.of(8,15,5.5), Set.of(Allergen.GLUTEN));
        IngredientPortion ip1 = IngredientPortion.of(latte, Quantity.of(200, Unit.MILLILITER));
        IngredientPortion ip2 = IngredientPortion.of(biscotti, Quantity.of(500, Unit.GRAM));
        List<IngredientPortion> ingredients = List.of(ip1, ip2);

        Dish colazione = new Dish.Builder().name("Colazione").description("Latte e biscotti").courseType(CourseType.BEVERAGE).dietCategory(DietCategory.TRADITIONAL).price(Money.of(4.50)).ingredients(ingredients).build();

        assertEquals("Colazione", colazione.name());
        assertTrue(colazione.isAllergenic());
        assertTrue(colazione.allergens().contains(Allergen.MILK));
        assertTrue(colazione.containsAllergen(Allergen.GLUTEN));
        assertTrue(colazione.totalKcal() > 0);
    }
}
