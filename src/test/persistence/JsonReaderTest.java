package persistence;

import model.Garden;
import model.Plant;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//tests the JsonReader class
//from JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Garden g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGarden.json");
        try {
            Garden g = reader.read();
            assertEquals("my garden", g.getName());
            assertEquals(0, g.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGarden.json");
        try {
            Garden g = reader.read();
            assertEquals("my garden", g.getName());
            List<Plant> plants = g.getPlants();
            assertEquals(2, g.size());
            checkPlant("A", "Succulent", 10, 0, 10, plants.get(0));
            checkPlant("Default", "Name",0, 0, 0, plants.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
