package ui;

import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//creates a button panel and implements what happens when button is pressed
public class ButtonGUI extends JPanel {
    private GardenGUI gardenGUI;
    private JButton addPlant;
    private JButton save;
    private JButton load;
    private JButton advanceDay;
    private JButton water;
    private ButtonListener bl;
    private JTextField getName;
    private JTextField getWater;
    private JTextField getType;
    private JRadioButton repotButton;
    private JRadioButton noRepotButton;

    //effects: constructs the buttonGUI panel
    public ButtonGUI(GardenGUI gardenGUI) {
        this.gardenGUI = gardenGUI;
        setLayout(new FlowLayout());
        addPlant = new JButton("add plant");
        save = new JButton("save");
        load = new JButton("load");
        advanceDay = new JButton("new day");
        water = new JButton("water");
        bl =  new ButtonListener();
        addPlant.addActionListener(bl);
        save.addActionListener(bl);
        load.addActionListener(bl);
        advanceDay.addActionListener(bl);
        water.addActionListener(bl);
        add(addPlant);
        add(save);
        add(load);
        add(advanceDay);
        add(water);
    }

    //new private class that takes care of what happens when a button is pressed
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addPlant) {
                try {
                    addPlant();
                } catch (NoOptionSelectedException ex) {
                    JOptionPane.showMessageDialog(gardenGUI, "please complete all blanks... "
                            + "cancelling plant in progress...");
                    return;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gardenGUI, "please enter a positive and nonzero integer... "
                            + "cancelling plant in progress...");
                    return;
                }
            } else if (e.getSource() == save) {
                gardenGUI.saveGarden();
            } else if (e.getSource() == advanceDay) {
                newDay();
            } else if (e.getSource() == water) {
                water();
            } else { //load option
                gardenGUI.loadGarden();
                gardenGUI.drawPlants();
            }
            return;
        }

        //modifies: gardenGUI.garden
        //effects: advances the day of the garden and displays message to user
        private void newDay() {
            gardenGUI.getGarden().advanceDay();
            JOptionPane.showMessageDialog(gardenGUI, "it's a new day!\nthere are "
                    + gardenGUI.getGarden().notifications() + " plants that need water");
            return;
        }

        //modifies: gardenGUI.garden
        //effects: waters the plants of the garden and shows message to user
        private void water() {
            gardenGUI.getGarden().water();
            JOptionPane.showMessageDialog(gardenGUI, "plants that need water have been watered");
            return;
        }

        //modifies: garden object in gardenGUI frame
        //effects: attempts to add plant to garden according to user input, throws exceptions if input format not right
        private void addPlant() throws NumberFormatException, NoOptionSelectedException {
            if (gardenGUI.getGarden().size() < 9) {
                Plant newPlant;
                int water;
                int repot;
                JPanel toAsk = createPlantPanel();
                int result = JOptionPane.showConfirmDialog(gardenGUI, toAsk, "adding plant...",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.CANCEL_OPTION) {
                    return;
                }
                repot = getRepotFromUser();
                water = Integer.parseInt(getWater.getText());
                if (water <= 0) {
                    throw new NumberFormatException("0 or negative integer selected");
                }
                if (result == JOptionPane.OK_OPTION) {
                    newPlant = new Plant(getName.getText(), getType.getText(), water, repot);
                    gardenGUI.getGarden().addPlant(newPlant);
                    gardenGUI.drawPlants();
                }
            }
        }

        //effects: checks if name, type, and repot fields are filled in correctly then assigns value for repot
        private int getRepotFromUser() throws NoOptionSelectedException {
            int repot;
            if (getName.getText() == null || getType.getText() == null
                    || (!repotButton.isSelected() && !noRepotButton.isSelected())) {
                throw new NoOptionSelectedException("no option selected");
            } else if (repotButton.isSelected()) {
                repot = 14;
            } else { //noRepotButton selected
                repot = -1;
            }
            return repot;
        }

        //modifies: this
        //effects: creates the labels for the pop out window and adds to panel
        private JPanel createPlantPanel() {
            JPanel askUser = new JPanel();
            askUser.setLayout(new BoxLayout(askUser, BoxLayout.Y_AXIS));
            initializePanel();
            JLabel askName = new JLabel("enter name: ");
            JLabel askType = new JLabel("enter type: ");
            JLabel askWater = new JLabel("how often should new plant be watered (in days): ");
            JLabel askRepot = new JLabel("is the plant new from the store? ");
            ButtonGroup bg = new ButtonGroup();
            bg.add(repotButton);
            bg.add(noRepotButton);
            askUser.add(askName);
            askUser.add(getName);
            askUser.add(askType);
            askUser.add(getType);
            askUser.add(askWater);
            askUser.add(getWater);
            askUser.add(askRepot);
            askUser.add(repotButton);
            askUser.add(noRepotButton);
            return askUser;
        }

        //modifies: this
        //effects: creates text fields and radio buttons for the pop out window
        private void initializePanel() {
            getName = new JTextField(7);
            getType = new JTextField(7);
            getWater = new JTextField(7);
            repotButton = new JRadioButton("yes");
            noRepotButton = new JRadioButton("no");
        }
    }
}
