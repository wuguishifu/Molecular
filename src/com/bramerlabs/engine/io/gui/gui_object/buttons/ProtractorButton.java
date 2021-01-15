package com.bramerlabs.engine.io.gui.gui_object.buttons;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.io.gui.gui_render.GuiMesh;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;

import java.util.ArrayList;

public class ProtractorButton extends Button {

    // the ID of protractor buttons
    public static final int ID = Button.BUTTON_PROTRACTOR;

    // the different textures
    private static final String unpressedTexture = "/textures/buttons/button_protractor.png";
    private static final String pressedTexture = "/textures/buttons/button_protractor_pressed.png";

    /**
     * default constructor for specified position and size
     * @param x - the x position
     * @param y - the y position
     * @param width - the width
     * @param height - the height
     * @param defaultMesh - the default unpressed button state
     * @param stateMesh - the pressed button state
     */
    public ProtractorButton(float x, float y, float width, float height, GuiMesh defaultMesh, GuiMesh stateMesh) {
        super(x, y, width, height, defaultMesh, stateMesh, ID);
    }

    /**
     * creates an instance of an protractor button
     * @param position - the position of the button [x, y]
     * @param size - the size of the button [width, height]
     * @return - a new ProtractorButton
     */
    public static ProtractorButton getInstance(Vector2f position, Vector2f size) {
        float x = position.getX();
        float y = position.getY();
        float width = size.getX();
        float height = size.getY();

        // create the VAO and IBO
        Vertex[] vertices = new Vertex[]{
                new Vertex(new Vector2f(x, y), new Vector2f(0, 1)),
                new Vertex(new Vector2f(x + width, y), new Vector2f(1, 1)),
                new Vertex(new Vector2f(x + width, y + height), new Vector2f(1, 0)),
                new Vertex(new Vector2f(x, y + height), new Vector2f(0, 0)),
        };
        int[] indices = new int[]{
                0, 1, 2,
                0, 2, 3
        };

        // create the materials
        Material unpressedMaterial = new Material(unpressedTexture);
        Material pressedMaterial = new Material(pressedTexture);

        // construct the new information button
        return new ProtractorButton(position.getX(), position.getY(), size.getX(), size.getY(),
                new GuiMesh(vertices, indices, unpressedMaterial),
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
    public static ProtractorButton getInstance(int x1, int y1, int width, int height, Window window) {
        float windowWidth = window.getWidth();
        float windowHeight = window.getHeight();
        return ProtractorButton.getInstance(new Vector2f(x1 / windowWidth - 1f, y1 / windowHeight - 1f), new Vector2f(width / windowWidth, height / windowHeight));
    }

    @Override
    public void onClick(ArrayList<Atom> atoms) {
        if (atoms.size() < 3) {
            return;
        }
        Vector3f v1 = Vector3f.subtract(atoms.get(0).getPosition(), atoms.get(1).getPosition());
        Vector3f v2 = Vector3f.subtract(atoms.get(2).getPosition(), atoms.get(1).getPosition());
        System.out.println(Vector3f.angleBetween(v1, v2));
    }
}