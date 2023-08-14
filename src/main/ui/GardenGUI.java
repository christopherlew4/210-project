package ui;

import model.EventLog;
import model.Garden;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//creates a frame for the gardenGUI and stores panels within
public class GardenGUI extends JFrame {
    private static final String JSON_STORE = "./data/garden.json";
    private PlantsGUI plot;
    private ButtonGUI buttons;
    private Garden garden;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private CloseWindowListener cwl;

    //effects: constructs a garden gui
    public GardenGUI() {
        super("my garden");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        garden = new Garden();
        createPlot();
        createButtons();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        pack();
        setSize(500,500);
        cwl = new CloseWindowListener();
        addWindowListener(cwl);
        setVisible(true);
    }

    //effects: returns the garden object
    public Garden getGarden() {
        return garden;
    }

    //modifies: this
    //effects: makes a plot panel and adds to frame
    private void createPlot() {
        plot = new PlantsGUI(this);
        add(plot, BorderLayout.CENTER);
        return;
    }

    //modifies: this
    //effects: makes a panel with the buttons and adds to frame
    private void createButtons() {
        buttons = new ButtonGUI(this);
        add(buttons, BorderLayout.PAGE_END);
        return;
    }

    //effects: updates the plant panel
    public void drawPlants() {
        plot.drawPlants();
        repaint();
        return;
    }

    //effects: saves the garden to a file, shows message dialog confirming whether saved or not
    public void saveGarden() {
        try {
            jsonWriter.open();
            jsonWriter.write(garden);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "saved " + garden.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "unable to write to file: " + JSON_STORE);
        }
    }

    //modifies: this
    //effects: loads the garden from a file if possible, shows message dialog confirming whether loaded or not
    public void loadGarden() {
        try {
            garden = jsonReader.read();
            JOptionPane.showMessageDialog(this, "loaded " + garden.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "unable to read from file: " + JSON_STORE);
        }
    }

    //effects: starts the application
    public static void main(String[] args) {
        new GardenGUI();
    }

    private class CloseWindowListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            return;
        }

        //effects: prints the event log to the console
        @Override
        public void windowClosing(WindowEvent e) {
            EventLog el = EventLog.getInstance();
            for (model.Event event : el) {
                System.out.println(event);
            }
            return;
        }

        @Override
        public void windowClosed(WindowEvent e) {
            return;
        }

        @Override
        public void windowIconified(WindowEvent e) {
            return;
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            return;
        }

        @Override
        public void windowActivated(WindowEvent e) {
            return;
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            return;
        }
    }
}
