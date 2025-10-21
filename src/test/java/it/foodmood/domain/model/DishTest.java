package it.foodmood.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.foodmood.domain.value.Allergen;
import it.foodmood.domain.value.CourseType;
import it.foodmood.domain.value.DietCategory;
import it.foodmood.domain.value.Image;
import it.foodmood.domain.value.IngredientPortion;
import it.foodmood.domain.value.Macronutrients;
import it.foodmood.domain.value.Money;
import it.foodmood.domain.value.Quantity;
import it.foodmood.domain.value.Unit;

class DishTest {
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

    @Test
    void builder_missingName(){
        assertThrows(IllegalArgumentException.class, () -> 
            new Dish.Builder()
                .courseType(CourseType.APPETIZER)
                .dietCategory(DietCategory.TRADITIONAL)
                .price(Money.of(5))
                .ingredients(List.of())
                .build()
            );
    }

    @Test
    void builder_emptyIngredients(){
        assertThrows(IllegalArgumentException.class, () -> 
            new Dish.Builder()
                .name("Test")
                .courseType(CourseType.APPETIZER)
                .dietCategory(DietCategory.TRADITIONAL)
                .price(Money.of(5))
                .build()
            );
    }

    @Test
    void description(){
        Ingredient i = Ingredient.of("Test", Macronutrients.zero(), Set.of());
        Dish d = new Dish.Builder()
            .name("Test")
            .description("Descrizione")
            .courseType(CourseType.APPETIZER)
            .dietCategory(DietCategory.TRADITIONAL)
            .price(Money.of(10))
            .addIngredient(IngredientPortion.of(i, Quantity.grams(50)))
            .build();

        assertTrue(d.description().isPresent());
        assertEquals("Descrizione", d.description().get());
    }

    @Test
    void image_whenPresent() throws Exception{
        var url = Objects.requireNonNull(getClass().getResource("/img/test/test.png"), "Risorsa '/img/test/test.png' non trovata nel percorso");
        URI uri = url.toURI();   
             
        Ingredient i = Ingredient.of("Test", Macronutrients.zero(), Set.of());
        Image img = Image.of(uri);
        Dish d = new Dish.Builder()
            .name("Test")
            .description("Descrizione")
            .courseType(CourseType.APPETIZER)
            .dietCategory(DietCategory.TRADITIONAL)
            .price(Money.of(10))
            .image(img)
            .addIngredient(IngredientPortion.of(i, Quantity.grams(50)))
            .build();

        assertTrue(d.image().isPresent());
        assertEquals(img, d.image().get());
    }

    @Test
    void equals_sameName(){
        Ingredient i = Ingredient.of("Test", Macronutrients.zero(), Set.of());
        Dish d1 = new Dish.Builder()
            .name("Test")
            .description("Descrizione")
            .courseType(CourseType.APPETIZER)
            .dietCategory(DietCategory.TRADITIONAL)
            .price(Money.of(10))
            .addIngredient(IngredientPortion.of(i, Quantity.grams(50)))
            .build();

        Dish d2 = new Dish.Builder()
            .name("Test")
            .description("Descrizione")
            .courseType(CourseType.APPETIZER)
            .dietCategory(DietCategory.TRADITIONAL)
            .price(Money.of(10))
            .addIngredient(IngredientPortion.of(i, Quantity.grams(50)))
            .build();

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void containsAllergen_withNull(){
        Ingredient i = Ingredient.of("Test", Macronutrients.zero(), Set.of());
        Dish d1 = new Dish.Builder()
            .name("Test")
            .description("Descrizione")
            .courseType(CourseType.APPETIZER)
            .dietCategory(DietCategory.TRADITIONAL)
            .price(Money.of(10))
            .addIngredient(IngredientPortion.of(i, Quantity.grams(50)))
            .build();
        assertThrows(NullPointerException.class, () -> d1.containsAllergen(null));
    }
}
