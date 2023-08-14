package ui;

import java.io.FileNotFoundException;

//runs the garden console
public class GardenConsoleMain {
    //effects: starts the application
    public static void main(String[] args) {
        try {
            new GardenConsoleApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
