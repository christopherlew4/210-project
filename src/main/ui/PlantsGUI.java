package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

//represents the plot of land that holds the elementGUIs
public class PlantsGUI extends JPanel {
    private static final int DIMENSION = 500;
    private GardenGUI gardenGUI;
    private ImageIcon plant1;
    private ImageIcon plant2;
    private ImageIcon plant3;
    private ArrayList<ElementGUI> elements;
    private ClickListener cl;

    //effects: constructs a plantsGUI
    public PlantsGUI(GardenGUI garden) {
        this.gardenGUI = garden;
        setLayout(new GridLayout(3,3));
        loadImages();
        cl = new ClickListener();
        elements = new ArrayList<>();
        initializeList();
        setSize(DIMENSION, DIMENSION);
        setVisible(true);
    }

    //modifies: this
    //effects: adds the elementGUIs to the panel
    private void initializeList() {
        for (int i = 0; i < 9; i++) {
            ElementGUI element = new ElementGUI();
            elements.add(element); //add to array list
            element.addMouseListener(cl);
            add(element); //add to frame
        }
        return;
    }

    //effects: generates a number between 1-3 (inclusive)
    private int generateRandomNumber() {
        return (int)(Math.random() * (4 - 1) + 1);
    }

    //modifies: this
    //effects: draws the plants on the panel according to the garden from gardenGUI
    public void drawPlants() {
        int i;
        for (i = 0; i < gardenGUI.getGarden().getPlants().size(); i++) {
            if (elements.get(i).getImage().getIcon() == null) {
                int plantIcon = generateRandomNumber();
                setImage(plantIcon, i);
                elements.get(i).setPlant(gardenGUI.getGarden().getPlants().get(i));
            }
        }
        for (int j = i; j < 9; j++) {
            if (elements.get(j).getImage() != null) {
                elements.get(j).setImage(null);
                elements.get(j).setPlant(null);
            }
        }
    }

    //modifies: elementGUI
    //effects: sets the icon of the elementGUI according to random number
    private void setImage(int number, int i) {
        switch (number) {
            case 1:
                elements.get(i).setImage(plant1);
                break;
            case 2:
                elements.get(i).setImage(plant2);
                break;
            default:
                elements.get(i).setImage(plant3);
                break;
        }
        return;
    }

    //modifies: this
    //effects: loads the images from files in the images folder
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        plant1 = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "plant1.png");
        plant2 = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "plant2.png");
        plant3 = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "plant3.png");
    }

    //private class that handles when there is a click on the screen
    private class ClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            return;
        }

        @Override
        //effects: plant name and type pop out in a window when the elementGUI is clicked
        public void mousePressed(MouseEvent e) {
            try {
                ElementGUI current = (ElementGUI)e.getSource();
                JOptionPane.showMessageDialog(gardenGUI, "name: " + current.getPlant().getName()
                        + "\ntype: " + current.getPlant().getType() + "\nneeds to be watered in: "
                        + current.getPlant().getCurrentWater() + " days");
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(gardenGUI, "no plant here yet!");
                return;
            }
            return;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            return;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            return;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            return;
        }
    }
}
