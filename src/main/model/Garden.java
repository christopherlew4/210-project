package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//represents a garden (a collection of plants)
public class Garden implements Writable {
    public static final int MAX_SIZE = 9;
    private String name;
    private List<Plant> garden;

    //effects: creates a new array list of plants
    public Garden(String name) {
        this.name = name;
        garden = new ArrayList<>();
    }

    //effects: default garden constructor with name "my garden"
    public Garden() {
        this("my garden");
    }

    //modifies: this
    //effects: adds a plant to the garden if the max size has not been reached
    public boolean addPlant(Plant p) {
        if (garden.size() >= MAX_SIZE) {
            return false;
        }
        Event e = new Event(p.getName() + " added to garden");
        EventLog.getInstance().logEvent(e);
        garden.add(p);
        return true;
    }

    //remove plant

    //modifies: this
    //effects: goes through each plant in garden and waters if necessary
    public void water() {
        for (Plant p : garden) {
            if (p.getCurrentWater() <= 0) {
                p.setCurrentWater(p.getDaysWithoutWater());
            }
        }
        Event e = new Event("garden watered");
        EventLog.getInstance().logEvent(e);
        return;
    }

    //modifies: this
    //effects: goes through each plant in garden and repots if necessary
    public void repot() {
        for (Plant p : garden) {
            if (p.getDaysToRepot() <= 0) {
                p.setDaysToRepot(-1);
            }
        }
        return;
    }

    //modifies: this
    //effects: decreases # for currentWater and daysToRepot for each plant if applicable
    public void advanceDay() {
        for (Plant p : garden) {
            p.setCurrentWater(p.getCurrentWater() - 1);
            int needRepot = p.getDaysToRepot();
            if (needRepot > 0) {
                p.setDaysToRepot(needRepot - 1);
            }
        }
        Event e = new Event("new day");
        EventLog.getInstance().logEvent(e);
        return;
    }

    //effects: prints out the plants in the garden
    public String print() {
        String currentGarden = "[ ";
        for (Plant p : garden) {
            currentGarden += p.getName() + " ";
        }
        currentGarden += "]";
        return currentGarden;
    }

    //effects: returns the # of plants in the garden
    public int size() {
        return garden.size();
    }

    //effects: returns number of plants that need water in the garden
    public int notifications() {
        int toBeWatered = 0;
        for (Plant p : garden) {
            if (p.getCurrentWater() <= 0) {
                toBeWatered++;
            }
        }
        return toBeWatered;
    }

    //effects: converts garden into json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("plants", plantsToJson());
        return json;
    }

    //effects: returns name of garden
    public String getName() {
        return name;
    }

    /*
    public void setName(String name) {
        this.name = name;
    }
    */

    //effects: returns a list of plants
    public List<Plant> getPlants() {
        return Collections.unmodifiableList(garden);
    }

    //effects: returns the plants in the garden as a JSONArray
    private JSONArray plantsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Plant p: garden) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }
}
