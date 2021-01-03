package com.bramerlabs.engine.collision_detection;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;

public class SphericalHitbox {

    // the position of the focus of this spherical hitbox
    private Vector3f position;

    // the radius of this hitbox
    private float radius;

    // the atom that this hitbox corresponds to
    private Atom atom;

    /**
     * default constructor
     * @param position - the position of the center of this hitbox
     * @param radius - the radius of this hitbox
     * @param atom - the atom this hitbox corresponds to
     */
    public SphericalHitbox(Vector3f position, float radius, Atom atom) {
        this.position = position;
        this.radius = radius;
        this.atom = atom;
    }

    /**
     * determines if a vector intersects this hitbox
     * @param v - the vector
     * @return - true if the vector intersects this hitbox
     */
    public boolean intersects(Vector3f v) {
        float length = Vector3f.length(Vector3f.subtract(position, v));
        return length < radius;
    }

    /**
     * getter method
     * @return - the position of the center of this spherical hitbox
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * getter method
     * @return - the radius of this spherical hitbox
     */
    public float getRadius() {
        return this.radius;
    }

    /**
     * getter method
     * @return - the atom this hitbox corresponds to
     */
    public Atom getAtom() {
        return this.atom;
    }
}