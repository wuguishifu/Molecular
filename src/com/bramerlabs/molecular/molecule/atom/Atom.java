package com.bramerlabs.molecular.molecule.atom;

import com.bramerlabs.engine.collision_detection.SphericalHitbox;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.shapes.Sphere;

public class Atom {

    // the position of the atom - default (0, 0, 0)
    private Vector3f position = new Vector3f(0, 0, 0);

    // the radius of the atom - default radius 1
    private float radius = 1;

    // the color of this atom - default gray
    private Vector3f color = new Vector3f(0.5f);

    // the sphere used for rendering the atom
    private Sphere sphere;

    // the hitbox of this atom
    private SphericalHitbox hitbox;

    /**
     * default constructor
     */
    public Atom() {
        makeSphere();
        makeHitbox();
    }

    /**
     * constructor for specified position
     * @param position - the position of the atom
     */
    public Atom(Vector3f position) {
        this.position = position;
        makeSphere();
        makeHitbox();
    }

    /**
     * constructor for position and size of the atom
     * @param position - the position of the atom
     * @param radius - the radius of the atom
     */
    public Atom(Vector3f position, float radius) {
        this.position = position;
        this.radius = radius;
        makeSphere();
        makeHitbox();
    }

    /**
     * constructor for position, size, and color of the atom
     * @param position - the position of the atom
     * @param radius - the radius of the atom
     * @param color - the color of the atom
     */
    public Atom(Vector3f position, float radius, Vector3f color) {
        this.position = position;
        this.radius = radius;
        this.color = color;
        makeSphere();
        makeHitbox();
    }

    /**
     * makes the hitbox for this atom
     */
    private void makeHitbox() {
        hitbox = new SphericalHitbox(position, radius, this);
    }

    /**
     * makes the sphere used for rendering this atom
     */
    private void makeSphere() {
        sphere = Sphere.makeSphere(position, color, radius);
        sphere.createMesh();
    }

    /**
     * getter method
     * @return - the sphere used to render this atom
     */
    public Sphere getSphere() {
        return sphere;
    }

    /**
     * getter method
     * @return - the hitbox
     */
    public SphericalHitbox getHitbox() {
        return this.hitbox;
    }

    /**
     * getter method
     * @return - the radius of this atom
     */
    public float getRadius() {
        return this.radius;
    }

    /**
     * getter method
     * @return - the position of this atom
     */
    public Vector3f getPosition() {
        return this.position;
    }

    /**
     * getter method
     * @return - the color of the atom
     */
    public Vector3f getColor() {
        return this.color;
    }
}