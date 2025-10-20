package it.foodmood.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void equals(){
        Macronutrients m1 = Macronutrients.of(7, 60, 1);
        Macronutrients m2 = Macronutrients.of(7, 60, 1);
        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
        assertTrue(m1.toString().contains("Proteine"));
    }
}
