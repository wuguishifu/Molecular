package com.bramerlabs.engine.io.gui.gui_object.buttons;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.io.gui.gui_object.GuiObject;
import com.bramerlabs.engine.io.gui.gui_render.GuiMesh;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;

public class Button extends GuiObject {

    // different buttons
    public static final int BUTTON_INFORMATION = 1000;
    public static final int BUTTON_SAVE = 1001;
    public static final int BUTTON_NEW_FILE = 1002;

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
     * @param defaultMesh - the default unpressed button state
     * @param stateMesh - the pressed button state
     * @param x - the x position
     * @param y - the y position
     * @param width - the width
     * @param height - the height
     */
    public Button(float x, float y, float width, float height, GuiMesh defaultMesh, GuiMesh stateMesh) {
        super(defaultMesh, stateMesh, new Vector2f(x, y), new Vector2f(width, height));
        this.createMesh();
    }

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
     * gets an instance of a button at a certain position with a certain size
     * @param x - the viewport x coord
     * @param y - the viewport y coord
     * @param width - the viewport width
     * @param height - the viewport height
     * @return - a new button
     */
    public static Button getInstance(float x, float y, float width, float height, String pathToDefaultTexture, String pathToPressedTexture) {
        Vertex[] vertices = new Vertex[]{
//                new Vertex(new Vector2f(-0.5f, -0.5f), new Vector2f(0, 1)),
//                new Vertex(new Vector2f( 0.5f, -0.5f), new Vector2f(1, 1)),
//                new Vertex(new Vector2f( 0.5f,  0.5f), new Vector2f(1, 0)),
//                new Vertex(new Vector2f(-0.5f,  0.5f), new Vector2f(0, 0)),
                new Vertex(new Vector2f(x, y), new Vector2f(0, 1)),
                new Vertex(new Vector2f(x + width, y), new Vector2f(1, 1)),
                new Vertex(new Vector2f(x + width, y + height), new Vector2f(1, 0)),
                new Vertex(new Vector2f(x, y + height), new Vector2f(0, 0)),
        };
        int[] indices = new int[]{
                0, 1, 2,
                0, 2, 3
        };

        // makes the two meshes
        Material defaultMaterial = new Material(pathToDefaultTexture);
        Material pressedMaterial = new Material(pathToPressedTexture);

        // make the new button
        return new Button(x, y, width, height,
                new GuiMesh(vertices, indices, defaultMaterial),
                new GuiMesh(vertices, indices, pressedMaterial)
        );
    }

    /**
     * gets an instance of a button at a certain position with a certain size
     * @param x1 - the absolute x position
     * @param y1 - the absolute y position
     * @param width - the absolute width
     * @param height - the absolute height
     * @param window - the window
     * @return - the button
     */
    public static Button getInstance(int x1, int y1, int width, int height, Window window, String pathToDefaultTexture, String pathToPressedTexture) {
        float windowWidth = window.getWidth();
        float windowHeight = window.getHeight();
        return getInstance(x1/windowWidth - 1f, y1/windowHeight - 1f, width/windowWidth, height/windowHeight, pathToDefaultTexture, pathToPressedTexture);
    }

    /**
     * gets an instance of a button from maxima
     * @param x1 - the min x value
     * @param y1 - the min y value
     * @param x2 - the max x value
     * @param y2 - the max y value
     * @return - a new button
     */
    public static Button getInstanceFromMaxima(int x1, int y1, int x2, int y2, String pathToDefaultTexture, String pathToPressedTexture) {
        return getInstance(x1, y1, x2 - x1, y2 - y1, pathToDefaultTexture, pathToPressedTexture);
    }
}
