package it.foodmood.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class QuantityTest {
    @Test
    void of_invalidAmount(){
        assertThrows(IllegalArgumentException.class, () -> Quantity.of(0, Unit.GRAM));
        assertThrows(IllegalArgumentException.class, () -> Quantity.of(-5, Unit.MILLILITER));
    }

    @Test
    void of_nullUnit(){
        assertThrows(NullPointerException.class, () -> Quantity.of(10, null));
    }

    @Test
    void ratioToBase_returnsFraction(){
        Quantity q = Quantity.of(50, Unit.GRAM);
        assertEquals(0.5, q.ratioToBase(), 1e-9);
    }

    @Test
    void equals(){
        Quantity q1 = Quantity.of(200, Unit.MILLILITER);
        Quantity q2 = Quantity.of(200, Unit.MILLILITER);
        assertEquals(q1, q2);
        assertEquals(q1.hashCode(), q2.hashCode());
        assertTrue(q1.toString().contains("ml"));
    }
}
