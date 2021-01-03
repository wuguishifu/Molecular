package com.bramerlabs.engine.objects;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.math.Vector3f;

public class RenderObject {

    // object location data
    private Vector3f position, rotation, scale;

    // the mesh that this object is made of
    private Mesh mesh;

    // the objects hitbox
    private Hitbox hitbox;

    /**
     * default constructor for specified values
     * @param mesh - the mesh that this object is made of
     * @param position - the position of this object
     * @param rotation - the rotation of this object
     * @param scale - the scale of this object
     */
    public RenderObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;

        // create a hitbox
        Vector3f min = Vector3f.subtract(position, Vector3f.scale(scale, 0.5f));
        Vector3f max = Vector3f.add(position, Vector3f.scale(scale, 0.5f));
        hitbox = new Hitbox(min.getX(), max.getX(), min.getY(), max.getY(), min.getZ(), max.getZ());
    }

    /**
     * creates the mesh
     */
    public void createMesh() {
        mesh.create();
    }

    /**
     * releases the game object
     */
    public void destroy() {
        mesh.destroy();
    }

    /**
     * getter method
     * @return - the position of this object
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * sets the position of this game object
     * @param position - the new position of this object
     */
    public void setPosition(Vector3f position) {
        this.position = position;

        // set the hitbox positions
        Vector3f min = Vector3f.subtract(position, Vector3f.scale(scale, 0.5f));
        Vector3f max = Vector3f.add(position, Vector3f.scale(scale, 0.5f));
        hitbox.setValues(min.getX(), max.getX(), min.getY(), max.getY(), min.getZ(), max.getZ());
    }

    /**
     * getter method
     * @return - the rotation of this object
     */
    public Vector3f getRotation() {
        return rotation;
    }

    /**
     * sets the rotation of this game object
     * @param rotation - the new rotation
     */
    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    /**
     * getter method
     * @return - the scale of this object
     */
    public Vector3f getScale() {
        return scale;
    }

    /**
     * sets the scale of this game object
     * @param scale - the new scale
     */
    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    /**
     * getter method
     * @return - the mesh that this object is made of
     */
    public Mesh getMesh() {
        return mesh;
    }

    /**
     * getter method
     * @return - the hitbox of this game object
     */
    public Hitbox getHitbox() {
        return this.hitbox;
    }
}
