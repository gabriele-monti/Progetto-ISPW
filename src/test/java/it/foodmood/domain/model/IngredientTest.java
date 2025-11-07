package it.foodmood.domain.model;

import java.util.Set;

import org.junit.jupiter.api.Test;

import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.Macronutrients;
import it.foodmood.domain.value.Quantity;
import it.foodmood.domain.value.Unit;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {
    @Test
    void testIngredientCreation(){
        Macronutrients macro = Macronutrients.of(10,5,2);
        Set<Allergen> allergens = Set.of(Allergen.MILK);
        Ingredient ingredient = Ingredient.of("Latte", macro, allergens);

        assertEquals("Latte", ingredient.getName());
        assertEquals(macro, ingredient.getMacro());
        assertTrue(ingredient.isAllergenic());
        assertTrue(ingredient.contains(Allergen.MILK));
    }

    @Test
    void testKcalForQuantity(){
        Macronutrients macro = Macronutrients.of(10,5,2);
        Ingredient ingredient = Ingredient.of("Latte", macro, Set.of());
        Quantity q = Quantity.of(50, Unit.GRAM);

        double expected = macro.kcal() * 0.5;
        assertEquals(expected, ingredient.kcalFor(q));
    }

    @Test
    void testToStringFormat(){
        Set<Allergen> allergens = Set.of(Allergen.CELERY);
        Ingredient ingredient = Ingredient.of("Pane", Macronutrients.of(7,60,1), allergens);
        assertTrue(ingredient.toString().contains("Pane"));
    }
}
