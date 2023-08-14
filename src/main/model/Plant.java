package model;

import org.json.JSONObject;
import persistence.Writable;

//represents a plant with a name, type, # of days between waterings, and if a new plant from a store keeps track
//of when it should be repotted
public class Plant implements Writable {

    private String name;
    private String type;
    private int daysWithoutWater;
    private int daysToRepot;
    private int currentWater;

    //effects: plant has given name, type, recommended days without water and days before repotting
    public Plant(String name, String type, int daysWithoutWater, int daysToRepot, int currentWater) {
        this.type = type;
        this.name = name;
        this.daysWithoutWater = daysWithoutWater;
        this.daysToRepot = daysToRepot;
        this.currentWater = currentWater;
    }

    //effects: creates a plant that is new (not saved before)
    public Plant(String name, String type, int daysWithoutWater, int daysToRepot) {
        this(name, type, daysWithoutWater, daysToRepot, daysWithoutWater);
    }

    //effects: creates a basic plant that you can edit later
    public Plant() {
        this("Default", "Name", 0, 0);
    }

    //effects: returns the days to go until recommended watering
    public int getCurrentWater() {
        return currentWater;
    }

    //modifies: this
    //effects: changes currentWater to given value
    public void setCurrentWater(int currentWater) {
        this.currentWater = currentWater;
    }

    //effects: returns the name of the plant
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    //effects: returns the # of days the plant should go without water
    public int getDaysWithoutWater() {
        return daysWithoutWater;
    }

    //effects: returns # of days left before repotting is recommended
    public int getDaysToRepot() {
        return daysToRepot;
    }

    //modifies: this
    //effects: changes daysToRepot to given value
    public void setDaysToRepot(int daysToRepot) {
        this.daysToRepot = daysToRepot;
    }

    @Override
    //effects: returns a json object of the plant object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", type);
        json.put("days without water", daysWithoutWater);
        json.put("days to repot", daysToRepot);
        json.put("current water", currentWater);
        return json;
    }
}
