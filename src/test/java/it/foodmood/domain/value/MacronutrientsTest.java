package it.foodmood.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MacronutrientsTest {
    @Test
    void of_validValues(){
        Macronutrients m1 = Macronutrients.of(20, 20, 4);
        assertEquals(196.0, m1.kcal(), 1e-9);

        Macronutrients m2 = m1.scale(0.5);
        assertEquals(98.0, m2.kcal(), 1e-9);
    }

    @Test
    void of_negativeValues(){
        assertThrows(IllegalArgumentException.class, () -> Macronutrients.of(-1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> Macronutrients.of(0, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> Macronutrients.of(0, 0, -1));

    }

    @Test
    void createZeroMacros(){
        Macronutrients m = Macronutrients.zero();
        assertEquals(0, m.getProtein());
        assertEquals(0, m.getCarbohydrates());
        assertEquals(0, m.getFat());
        assertEquals(0, m.kcal());
    }

    @Test
    void invalidRatio(){
        Macronutrients m = Macronutrients.of(10,10,10);
        assertThrows(IllegalArgumentException.class, () -> m.scale(-1));
        assertThrows(IllegalArgumentException.class, () -> m.scale(Double.NaN));
    }

    @Test
    void plusMacronutrients(){
        Macronutrients m1 = Macronutrients.of(10,10,10);
        Macronutrients m2 = Macronutrients.of(10,25,8);
        Macronutrients result = m1.plus(m2);

        assertEquals(20, result.getProtein());
        assertEquals(35, result.getCarbohydrates());
        assertEquals(18, result.getFat());
    }

    @Test
    void plusNull(){
        Macronutrients m = Macronutrients.of(10,10,10);
        assertThrows(NullPointerException.class, () -> m.plus(null));
    }

    @Test
    void equals(){
        Macronutrients m1 = Macronutrients.of(7, 60, 1);
        Macronutrients m2 = Macronutrients.of(7, 60, 1);
        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
        assertTrue(m1.toString().contains("Proteine"));
    }
}
