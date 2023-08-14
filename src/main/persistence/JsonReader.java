package persistence;

import model.Garden;
import model.Plant;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
//from JSONSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Garden read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGarden(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Garden parseGarden(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Garden g = new Garden(name);
        addPlants(g, jsonObject);
        return g;
    }

    // MODIFIES: g
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addPlants(Garden g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("plants");
        for (Object json : jsonArray) {
            JSONObject nextPlant = (JSONObject) json;
            addPlant(g, nextPlant);
        }
    }

    // MODIFIES: g
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addPlant(Garden g, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        int daysWoWater = jsonObject.getInt("days without water");
        int daysToRepot = jsonObject.getInt("days to repot");
        int currentWater = jsonObject.getInt("current water");
        Plant p = new Plant(name, type, daysWoWater, daysToRepot, currentWater);
        g.addPlant(p);
    }

}
