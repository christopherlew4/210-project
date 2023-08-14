package persistence;

import model.Garden;
import model.Plant;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//tests JsonWriter class
//taken from JSONSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Garden g = new Garden();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGarden() {
        try {
            Garden g = new Garden();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGarden.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGarden.json");
            g = reader.read();
            assertEquals("my garden", g.getName());
            assertEquals(0, g.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGarden() {
        try {
            Garden g = new Garden();
            Plant a = new Plant("A", "Succulent", 10,  0);
            Plant b = new Plant();
            g.addPlant(a);
            g.addPlant(b);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGarden.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGarden.json");
            g = reader.read();
            assertEquals("my garden", g.getName());
            List<Plant> plants = g.getPlants();
            assertEquals(2, g.size());
            checkPlant("A", "Succulent", 10, 0, 10, plants.get(0));
            checkPlant("Default", "Name", 0, 0, 0, plants.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
