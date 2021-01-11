package com.bramerlabs.molecular.molecule.atom;

import com.bramerlabs.engine.collision_detection.SphericalHitbox;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.shapes.Sphere;

public abstract class Atom {

    // identity data
    private int atomicNumber = 1; // the atomic number of this atom - default Hydrogen
    private String atomicAbbrName = "H"; // the abbreviated name - default Hydrogen
    private int charge = 0; // the charge of the atom - default neutral
    private int numNeutrons = 0; // the amount of neutrons this atom has - default 0

    // locational data
    private Vector3f position; // the position of the atom
    private float radius; // the radius of the atom

    // rendering
    private Vector3f color; // the color of this atom
    private Sphere sphere; // the sphere used for rendering the atom
    private SphericalHitbox hitbox; // the hitbox of this atom

    // selection variables
    private boolean isSelected = false; // if this atom is selected or not
    private Sphere selectionSphere; // the sphere used for rendering the selection box
    private Vector3f selectionColor = new Vector3f(0.5f, 0.5f, 0.f); // the color of the selection sphere - default yellow

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
        makeSelectionSphere();
        makeHitbox();
    }

    /**
     * constructor for specified position, radius, and identity
     * @param position - the position of the atom
     * @param radius - the radius of the atom
     * @param atomicNumber - the atomic number of the atom
     */
    public Atom(Vector3f position, float radius, int atomicNumber) {
        this.position = position;
        this.radius = radius;
        this.color = AtomicDataCompiler.getCPKColor(atomicNumber);

        this.atomicNumber = atomicNumber;

        makeSphere();
        makeSelectionSphere();
        makeHitbox();
    }

    /**
     * gets the atomic radius
     * @return - the atomic radius
     */
    public abstract float getAtomicRadius();

    /**
     * getter method
     * @return - the atomic number of this atom
     */
    public int getAtomicNumber() {
        return this.atomicNumber;
    }

    /**
     * getter method
     * @return - the charge of this atom
     */
    public int getCharge() {
        return this.charge;
    }

    /**
     * getter method
     * @return - the number of neutrons in this atom
     */
    public int getNumNeutrons() {
        return this.numNeutrons;
    }

    /**
     * getter method
     * @return - the abbreviated name of this atom
     */
    public String getAtomicAbbrName() {
        return this.atomicAbbrName;
    }

    /**
     * sets the atomic number of this atom
     * @param atomicNumber - the atomic number
     */
    public void setAtomicNumber(int atomicNumber) {
        this.atomicNumber = atomicNumber;
        this.atomicAbbrName = AtomicDataCompiler.getAtomAbbrName(atomicNumber);
    }

    /**
     * sets the charge of this atom
     * @param charge - the charge
     */
    public void setCharge(int charge) {
        this.charge = charge;
    }

    /**
     * sets the number of neutrons in this atom
     * @param neutrons - the number of neutrons
     */
    public void setNumNeutrons(int neutrons) {
        this.numNeutrons = neutrons;
    }

    /**
     * toggles the selection
     */
    public void toggleSelected() {
        this.isSelected = !isSelected;
    }

    /**
     * sets if this atom is selected or not
     * @param b - true or false
     */
    public void setSelected(boolean b) {
        this.isSelected = b;
    }

    /**
     * getter method
     * @return - a boolean representing if this atom is selected or not
     */
    public boolean isSelected() {
        return this.isSelected;
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
     * makes the sphere used for rendering the selection box of this atom
     */
    private void makeSelectionSphere() {
        selectionSphere = Sphere.makeSphere(position, selectionColor, radius + 0.1f);
        selectionSphere.createMesh();
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
     * @return - the sphere used to render the selection box of this atom
     */
    public Sphere getSelectionSphere() {
        return this.selectionSphere;
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