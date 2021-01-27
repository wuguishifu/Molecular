package com.bramerlabs.engine.io.gui;

import com.bramerlabs.engine.io.gui.gui_object.buttons.Button;

import java.util.ArrayList;

public class Gui {

    // the buttons in the GUI
    ArrayList<Button> buttons;

    /**
     * default constructor
     */
    public Gui() {
        this.buttons = new ArrayList<>();
    }

    /**
     * constructor for a list of buttons
     * @param buttons - the list of buttons
     */
    public Gui(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }

    /**
     * adds a button to the list of buttons
     * @param button - the button to be added
     */
    public void addButton(Button button) {
        this.buttons.add(button);
    }

    /**
     * removes a button from the list of buttons
     * @param button - the button to be removed
     */
    public void removeButton(Button button) {
        this.buttons.remove(button);
    }

    /**
     * gets a specified button
     * @param ID - the ID of the button
     * @return - the button at that index
     */
    public Button getButton(int ID) {
        for (Button button : buttons) {
            if (button.getID() == ID) return button;
        }
        return null;
    }

    /**
     * getter method
     * @return - the list of buttons in this gui
     */
    public ArrayList<Button> getButtons() {
        return this.buttons;
    }

    public String getButtonsAsStrings() {
        return null;
    }

}