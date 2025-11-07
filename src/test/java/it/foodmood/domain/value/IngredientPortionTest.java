package it.foodmood.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import it.foodmood.domain.model.Ingredient;

class IngredientPortionTest {
    
    @Test
        void of_createsEqualsPortions(){
        Ingredient latte = Ingredient.of("Latte", Macronutrients.of(4,3,3.5), Set.of(Allergen.MILK));
        Quantity q = Quantity.of(200, Unit.MILLILITER);

        IngredientPortion ingredient1 = IngredientPortion.of(latte, q);
        IngredientPortion ingredient2 = IngredientPortion.of(latte, Quantity.of(200, Unit.MILLILITER));

        assertEquals(ingredient1, ingredient2);
        assertEquals(ingredient2, ingredient1);
        assertTrue(ingredient1.toString().contains("Latte"));
    }

    @Test
    void of_nullValus(){
        Ingredient latte = Ingredient.of("Latte", Macronutrients.of(4,3,3.5), Set.of(Allergen.MILK));
        Quantity q = Quantity.of(1, Unit.GRAM);

        assertThrows(NullPointerException.class, () -> IngredientPortion.of(null, q));
        assertThrows(NullPointerException.class, () -> IngredientPortion.of(latte, null));
    }

    @Test
    void whithQuantity_createNewPortion(){
        Ingredient latte = Ingredient.of("Latte", Macronutrients.of(4,3,3.5), Set.of(Allergen.MILK));
        Quantity q1 = Quantity.grams(100);
        Quantity q2 = Quantity.grams(200);

        IngredientPortion ip1 = IngredientPortion.of(latte, q1);
        IngredientPortion ip2 = ip1.withQuantity(q2);

        assertEquals(q2, ip2.getQuantity());
        assertEquals(latte, ip2.getIngredient());
        assertNotEquals(ip1, ip2);
    }

    @Test
    void whithQuantity_null(){
        Ingredient latte = Ingredient.of("Latte", Macronutrients.of(4,3,3.5), Set.of(Allergen.MILK));
        Quantity q1 = Quantity.grams(100);
        IngredientPortion ip1 = IngredientPortion.of(latte, q1);

        assertThrows(NullPointerException.class, () -> ip1.withQuantity(null));
    }

    @Test
    void hashCode_verify(){
        Ingredient latte = Ingredient.of("Latte", Macronutrients.of(4,3,3.5), Set.of(Allergen.MILK));
        Quantity q1 = Quantity.grams(100);
        IngredientPortion ip1 = IngredientPortion.of(latte, q1);
        IngredientPortion ip2 = IngredientPortion.of(latte, q1);

        assertEquals(ip1.hashCode(), ip2.hashCode());
    }

}
