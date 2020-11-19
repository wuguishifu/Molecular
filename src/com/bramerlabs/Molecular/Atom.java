package com.bramerlabs.Molecular;

import com.bramerlabs.sphere.Sphere;

public class Atom {

    // atom position
    private float[] position;
    // default position
    private final float[] defaultPosition = {0, 0, 0};

    // atom identity
    private int atomicNumber;
    private int charge;
    private int neutrons;
    private int massNumber; // this is probably not going to get used if i decide to use the neutrons variable
    //default values
    private final int defaultAtomicNumber = 1;
    private final int defaultCharge = 0;
    private final int defaultNeutrons = 0;
    private final int defaultMassNumber = 0;

    // rendering the atom
    private float[] color; // in the form [r, g, b]
    private float[] shadedColor; // in the form [r, g, b]
    private Sphere sphere;
    // default values
    private final float[] defaultColor = {1f, 1f, 1f};
    private final float[] defaultShadedColor = {1f, 1f, 1f};

    // radius of sphere
    private float radius;
    //default value
    private float defaultRadius = 1f;

    /**
     * constructor for position and identity
     * @param x - x position
     * @param y - y position
     * @param z - z position
     * @param atomicNumber - the number of protons
     * @param charge - the charge (number of electrons - number of protons)
     * @param neutrons - the number of neutrons
     */
    public Atom(float x, float y, float z, int atomicNumber, int charge, int neutrons) {
        this.position = new float[]{x, y, z};
        this.atomicNumber = atomicNumber;
        this.charge = charge;
        this.neutrons = neutrons;
        radius = defaultRadius;
    }

    /**
     * constructor for position and identity
      * @param position - float array containing position in form [x, y, z]
     * @param identity - int array containing identity in form [atomicNumber, charge, neutron]
     */
    public Atom(float[] position, int[] identity) {

        if (position.length ==3 ) {
            this.position = position;
        } else {
            this.position = defaultPosition;
        }

        if (identity.length > 0) {
            atomicNumber = identity[0];
        }
        if (identity.length > 1) {
            this.charge = identity[1];
        }
        if (identity.length > 2) {
            this.neutrons = identity[2];
        }
        radius = defaultRadius;
    }

    /**
     * constructor for position
     * @param x - x position
     * @param y - y position
     * @param z - z position
     */
    public Atom(float x, float y, float z) {
        this.position = new float[]{x, y, z};
        this.atomicNumber = defaultAtomicNumber;
        this.charge = defaultCharge;
        this.neutrons = defaultNeutrons;
        radius = defaultRadius;
    }

    public Atom(float[] position) {
        if (position.length ==3 ) {
            this.position = position;
        } else {
            this.position = defaultPosition;
        }
        this.atomicNumber = defaultAtomicNumber;
        this.charge = defaultCharge;
        this.neutrons = defaultNeutrons;
        radius = defaultRadius;
    }

    /**
     * default constructor
     */
    public Atom() {
        this.position = defaultPosition;
        this.atomicNumber = defaultAtomicNumber;
        this.charge = defaultCharge;
        this.neutrons = defaultNeutrons;
        radius = defaultRadius;
    }

    /**
     * sets the color
     * @param color - the new color to be set
     * @return - this atom
     */
    public Atom setColor(float[] color) {
        this.color = color;
        return this;
    }

    /**
     * sets the shaded color
     * @param shadedColor - the shaded color
     * @return - this atom
     */
    public Atom setShadedColor(float[] shadedColor) {
        this.shadedColor = shadedColor;
        return this;
    }

    /**
     * creates a shaded color
     * @param shadeDifference - the difference
     * @return - this atom
     */
    public Atom setShadeDifference(float shadeDifference) {
        float r = Math.max(this.color[0] - shadeDifference, 0.0F);
        float g = Math.max(this.color[1] - shadeDifference, 0.0F);
        float b = Math.max(this.color[2] - shadeDifference, 0.0F);
        this.shadedColor = new float[]{r, g, b};
        return this;
    }

    public Atom setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    /**
     * sets the position
     * @param position - the new position
     */
    public void setPosition(float[] position) {
        if (position.length == 3) {
            this.setPosition(position);
        }
    }

    /**
     * creates a sphere
     * @return - a sphere
     */
    public Sphere getSphere() {
        return new Sphere(position, color, shadedColor);
    }

    @Override
    public String toString() {
        return "Position: (" + this.position[0] + ", " + this.position[1] + ", " + this.position[2] + "),\n" +
                "Atomic Number: " + this.atomicNumber + ", Charge: " + this.charge + "Mass Number: " + this.atomicNumber + this.neutrons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Atom)) return false;
        Atom atom = (Atom) o;
        return this.atomicNumber == atom.atomicNumber &&
                this.charge == atom.charge &&
                this.neutrons == atom.neutrons &&
                this.massNumber == atom.massNumber &&
                this.position == atom.position;
    }

    @Override
    public int hashCode() {
        return 31 * (int)this.position[0] + 313 * (int)this.position[1] + 3131 * (int)this.position[2] + atomicNumber + charge + neutrons;
    }
}