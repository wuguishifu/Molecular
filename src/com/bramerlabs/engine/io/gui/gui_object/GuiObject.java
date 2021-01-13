package com.bramerlabs.engine.io.gui.gui_object;

import com.bramerlabs.engine.io.gui.gui_render.GuiMesh;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;

public class GuiObject {

    // the current global ID - used to make sure every ID is unique
    public static int curID = 0;

    // the ID of this object
    public int ID;

    // object location and size data
    private Vector2f position, size;

    // the mesh that this object is made of
    private GuiMesh mesh;

    /**
     * default constructor for specified values
     * @param mesh - the mesh that this gui object is made of
     * @param position - the position of this object
     * @param size - the width and height of this object
     */
    public GuiObject(GuiMesh mesh, Vector2f position, Vector2f size) {
        this.mesh = mesh;
        this.position = position;
        this.size = size;
    }

    /**
     * creates the mesh
     */
    public void createMesh() {
        mesh.create();
    }

    /**
     * releases the gui object
     */
    public void destroy() {
        mesh.destroy();
    }

    /**
     * getter method
     * @return - the position of this gui object [x, y]
     */
    public Vector2f getPosition() {
        return this.position;
    }

    /**
     * getter method
     * @return - the size of this gui object [width, height]
     */
    public Vector2f getSize() {
        return this.size;
    }

    /**
     * getter method
     * @return - the mesh that this object is made of
     */
    public GuiMesh getMesh() {
        return this.mesh;
    }

    /**
     * generates a unique ID for this gui object
     */
    public void generateID() {
        this.ID = curID;
        curID++;
    }

    /**
     * sets the ID
     * @param ID - the new ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * getter method
     * @return - the ID of this object
     */
    public int getID() {
        return this.ID;
    }

    /**
     * getter method
     * @return - the transformation matrix
     */
    public Matrix4f getModel() {
        return Matrix4f.transform(new Vector3f(position, -1.0f), new Vector3f(0), new Vector3f(size, 1.0f));
    }

    /**
     * determines if a coordinate is within this button
     * @param x - the x position
     * @param y - the y position
     * @return - true if the (x, y) is within this button
     */
    public boolean containsCoords (float x, float y) {
        if (x > this.position.getX() + this.size.getX() || x < this.position.getX()) return false;
        return !(y > this.position.getY() + this.size.getY()) && !(y < this.position.getY());
    }
}