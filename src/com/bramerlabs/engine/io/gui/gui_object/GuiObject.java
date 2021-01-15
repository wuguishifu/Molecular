package com.bramerlabs.engine.io.gui.gui_object;

import com.bramerlabs.engine.io.gui.gui_render.GuiMesh;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;

public class GuiObject {

    // the current global ID - used to make sure every ID is unique
    public static int curID = 0;

    // the ID of this object
    private int ID;

    // object location and size data
    private Vector2f position, size;

    // the mesh that this object is made of
    private GuiMesh defaultMesh;
    private GuiMesh stateMesh;

    // the state of the object
    private int state = 0;

    /**
     * default constructor for specified values
     * @param mesh - the mesh that this gui object is made of
     * @param position - the position of this object
     * @param size - the width and height of this object
     */
    public GuiObject(GuiMesh mesh, Vector2f position, Vector2f size) {
        this.defaultMesh = mesh;
        this.position = position;
        this.size = size;
        generateID();
    }

    /**
     * default constructor for two mesh states
     * @param defaultMesh - the default state
     * @param stateMesh - the prime state
     * @param position - the position of this object
     * @param size - the width and height of this object
     */
    public GuiObject(GuiMesh defaultMesh, GuiMesh stateMesh, Vector2f position, Vector2f size) {
        this.defaultMesh = defaultMesh;
        this.stateMesh = stateMesh;
        this.position = position;
        this.size = size;
        generateID();
    }

    /**
     * default constructor for two mesh states and specified ID
     * @param defaultMesh - the default state
     * @param stateMesh - the prime state
     * @param position - the position of this object
     * @param size - the width and height of this object
     * @param ID - the ID of this button
     */
    public GuiObject(GuiMesh defaultMesh, GuiMesh stateMesh, Vector2f position, Vector2f size, int ID) {
        this.defaultMesh = defaultMesh;
        this.stateMesh = stateMesh;
        this.position = position;
        this.size = size;
        this.ID = ID;
    }

    /**
     * creates the mesh
     */
    public void createMesh() {
        defaultMesh.create();
        stateMesh.create();
    }

    /**
     * releases the gui object
     */
    public void destroy() {
        defaultMesh.destroy();
        stateMesh.destroy();
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
    public GuiMesh getDefaultMesh() {
        return this.defaultMesh;
    }

    /**
     * getter method
     * @return - the state mesh that this object is made of
     */
    public GuiMesh getStateMesh() {
        return this.stateMesh;
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

    /**
     * getter method
     * @return - the current state of the object
     */
    public int getState() {
        return this.state;
    }

    /**
     * sets the state
     * @param state - the new state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * resets the state
     */
    public void resetState() {
        this.state = 0;
    }
}