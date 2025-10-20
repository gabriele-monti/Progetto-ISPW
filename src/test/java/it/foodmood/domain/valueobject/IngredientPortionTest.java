package it.foodmood.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertThrows(NullPointerException.class, () -> IngredientPortion.of(null, Quantity.of(1, Unit.GRAM)));
        assertThrows(NullPointerException.class, () -> IngredientPortion.of(latte, null));
    }

}
