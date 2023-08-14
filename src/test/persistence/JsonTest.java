package persistence;

import model.Plant;
import static org.junit.jupiter.api.Assertions.assertEquals;

//tests an individual plant object
//from JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    //checks a single plant object with expected values
    protected void checkPlant(String name, String type, int daysWOWater, int daysToRepot, int currentWater, Plant p) {
        assertEquals(name, p.getName());
        assertEquals(type, p.getType());
        assertEquals(daysWOWater, p.getDaysWithoutWater());
        assertEquals(daysToRepot, p.getDaysToRepot());
        assertEquals(currentWater, p.getCurrentWater());
    }
}