package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GardenTest {
    private Garden gard;

    @BeforeEach
    public void runBefore() {
        gard = new Garden();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, gard.size());
        assertEquals("[ ]", gard.print());
    }

    @Test
    public void testAddPlant() {
        Plant a = new Plant("A", "Succulent", 10,  0);
        Plant b = new Plant();
        assertTrue(gard.addPlant(a));
        assertEquals("[ A ]", gard.print());
        for (int i = 0; i < 10; i++) {
            gard.addPlant(b);
        }
        assertFalse(gard.addPlant(a));
    }

    @Test
    public void testAdvanceDay() {
        Plant a = new Plant("A", "Succulent", 10,  0);
        Plant b = new Plant("B", "Bulb", 0, 3);
        gard.addPlant(a);
        gard.addPlant(b);
        gard.advanceDay();
        assertEquals(9, a.getCurrentWater());
        assertEquals(0, a.getDaysToRepot());
        assertEquals(-1, b.getCurrentWater());
        assertEquals(2, b.getDaysToRepot());
    }

    @Test
    public void testWater() {
        Plant a = new Plant("A", "Succulent", 1,  0);
        Plant b = new Plant("B", "Succulent", 10,  0);
        gard.addPlant(a);
        gard.addPlant(b);
        gard.advanceDay();
        gard.water();
        assertEquals(1, a.getCurrentWater());
        assertEquals(9, b.getCurrentWater());
    }

    @Test
    public void testRepot() {
        Plant a = new Plant("A", "Succulent", 10,  1);
        gard.addPlant(a);
        gard.repot();
        assertEquals(1, a.getDaysToRepot());
        gard.advanceDay();
        gard.repot();
        assertEquals(-1, a.getDaysToRepot());
    }

    @Test
    public void testNotifications() {
        Plant a = new Plant("A", "Succulent", 1,  1);
        gard.addPlant(a);
        assertEquals(0, gard.notifications());
        gard.advanceDay();
        assertEquals(1, gard.notifications());
        gard.water();
        assertEquals(0, gard.notifications());
    }
}