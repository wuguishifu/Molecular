package com.bramerlabs.engine.io.gui.gui_object.buttons;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.io.gui.gui_object.GuiObject;
import com.bramerlabs.engine.io.gui.gui_render.GuiMesh;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;

import java.util.ArrayList;

public abstract class Button extends GuiObject {

    // different buttons
    public static final int BUTTON_INFORMATION = 1000;
    public static final int BUTTON_SAVE = 1001;
    public static final int BUTTON_NEW_FILE = 1002;
    public static final int BUTTON_PROTRACTOR = 1003;
    public static final int BUTTON_SCREENSHOT = 1004;

    // the states of the button
    public static final int STATE_PRESSED = 1;
    public static final int STATE_RELEASED = 0;

    // the color of the button - unused
    private static Vector3f color = new Vector3f(1.0f, 1.0f, 1.0f);

    // the meshes this button is made of
    private Mesh defaultMesh; // the default mesh
    private Mesh stateMesh; // the button pressed mesh
    // the default paths
    public static String DEFAULT_UNPRESSED_MESH = "textures/buttons/button_default.png";
    public static String DEFAULT_PRESSED_MESH = "textures/buttons/button_default_pressed.png";

    /**
     * default constructor for specified position and size
     * @param x - the x position
     * @param y - the y position
     * @param width - the width
     * @param height - the height
     * @param defaultMesh - the default unpressed button state
     * @param stateMesh - the pressed button state
     * @param ID - the ID of the button
     */
    public Button(float x, float y, float width, float height, GuiMesh defaultMesh, GuiMesh stateMesh, int ID) {
        super(defaultMesh, stateMesh, new Vector2f(x, y), new Vector2f(width, height), ID);
        this.createMesh();
    }

    /**
     * handles the button press
     */
    public abstract void onClick(ArrayList<Atom> atoms);
}
