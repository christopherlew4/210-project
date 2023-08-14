package ui;

import model.Garden;
import model.Plant;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//garden console application
//readGarden and loadGarden from JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class GardenConsoleApp {
    private static final String JSON_STORE = "./data/garden.json";
    private Scanner input;
    private Garden garden;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //effects: runs the garden app
    public GardenConsoleApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        garden = new Garden();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runGarden();
    }

    //modifies: this
    //effects: processes user input
    private void runGarden() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        boolean keepGoing = true;
        String command = null;
        String save = null;
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
                System.out.println("Do you want to save the garden? (y/n)");
                save = input.next();
            } else {
                processCommand(command);
            }
        }
        if (save.equals("y")) {
            saveGarden();
        }
        System.out.println("Bye!");
    }

    //effects: displays menu to user
    private void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("- Add plant (a)");
        System.out.println("- Water plants (w)");
        System.out.println("- Repot plants (r)");
        System.out.println("- Next day (n)");
        System.out.println("- List garden (l)");
        System.out.println("- Load garden from file (f)");
        System.out.println("- Quit (q)");
        return;
    }

    //modifies: this
    //effects: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddPlant();
        } else if (command.equals("w")) {
            garden.water();
        } else if (command.equals("r")) {
            garden.repot();
        } else if (command.equals("n")) {
            garden.advanceDay();
            int plantsNeedWater = garden.notifications();
            if (plantsNeedWater != 0) {
                System.out.println("You have " + plantsNeedWater + " plants that need water!");
            }
        } else if (command.equals("l")) {
            System.out.println(garden.print());
        } else if (command.equals("f")) {
            loadGarden();
        } else {
            System.out.println("Command not valid. Try again.");
        }
        return;
    }

    //modifies: this
    //effects: adds plant to garden according to user input
    private void doAddPlant() {
        System.out.println("Name of new plant: ");
        String name = input.next();
        System.out.println("Type of new plant: ");
        String type = input.next();
        System.out.println("How often should it be watered (in days): ");
        int daysWithoutWater = input.nextInt();
        System.out.println("Is your plant new from the store? (y/n) ");
        int daysToRepot;
        String answer = input.next();
        if (answer.equals("y")) {
            daysToRepot = 14;
        } else {
            daysToRepot = -1;
        }
        Plant newPlant = new Plant(name, type, daysWithoutWater, daysToRepot);
        garden.addPlant(newPlant);
        return;
    }

    // EFFECTS: saves the garden to file
    private void saveGarden() {
        try {
            jsonWriter.open();
            jsonWriter.write(garden);
            jsonWriter.close();
            System.out.println("Saved " + garden.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads garden from file
    private void loadGarden() {
        try {
            garden = jsonReader.read();
            System.out.println("Loaded " + garden.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
