package ui;

import model.Plant;

import javax.swing.*;
import java.awt.*;

//represents a panel in a 3x3 grid
public class ElementGUI extends JPanel {
    private Plant plant;
    private JLabel image;

    //effects: constructs an elementGUI with a null icon for the label
    public ElementGUI() {
        plant = null;
        image = new JLabel();
        image.setIcon(null);
        setBackground(new Color(108, 75, 41));
        add(image);
        setVisible(true);
    }

    //effects: sets the plant
    public void setPlant(Plant toBeAdded) {
        plant = toBeAdded;
        return;
    }

    //effects: returns the Jlabel
    public JLabel getImage() {
        return image;
    }

    //effects: sets the icon for the Jlabel
    public void setImage(ImageIcon ic) {
        image.setIcon(ic);
        return;
    }

    //effects: gets the plant
    public Plant getPlant() {
        return plant;
    }

}
