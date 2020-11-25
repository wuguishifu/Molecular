package com.bramerlabs.molecular.molecule.atom;

import com.bramerlabs.sphere.Sphere;

public class Atom {

    // atom position
    private final float[] defaultPosition = {0, 0, 0};
    private float[] position;

    // atom identity
    private final int defaultAtomicNumber = 1;
    private final int defaultCharge = 0;
    private final int defaultNeutrons = 0;
    private int atomicNumber = defaultAtomicNumber;
    private int charge = defaultCharge;
    private int neutrons = defaultNeutrons;

    // rendering the atom
    private final float[] defaultColor = {1f, 1f, 1f};
    private final float[] defaultShadedColor = {1f, 1f, 1f};
    private float[] color = defaultColor; // in the form [r, g, b]
    private float[] shadedColor = defaultShadedColor; // in the form [r, g, b]

    // radius of sphere
    private float defaultRadius = 1f;
    private float radius = defaultRadius;

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
    }

    public float[] getPosition() {
        return this.position;
    }

    /**
     * constructor for position
     * @param position - float array containing position in form [x, y, z]
     */
    public Atom(float[] position) {
        if (position.length ==3 ) {
            this.position = position;
        } else {
            this.position = defaultPosition;
        }
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
    public Atom setShadedColor(float shadeDifference) {
        float r = Math.max(this.color[0] - shadeDifference, 0.0F);
        float g = Math.max(this.color[1] - shadeDifference, 0.0F);
        float b = Math.max(this.color[2] - shadeDifference, 0.0F);
        this.shadedColor = new float[]{r, g, b};
        return this;
    }

    /**
     * sets an atoms identity
     * @param identity - the new identity, in form [atomic number, charge, neutrons]
     * @return - this atom
     */
    public Atom setIdentity(int[] identity) {
        if (identity.length > 0) {
            atomicNumber = identity[0];
        }
        if (identity.length > 1) {
            this.charge = identity[1];
        }
        if (identity.length > 2) {
            this.neutrons = identity[2];
        }
        return this;
    }

    /**
     * sets the radius
      * @param radius - the new radius
     * @return - this atom
     */
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
        Sphere sphere = new Sphere(position, color, shadedColor);
        sphere.setRadius(radius);
        return sphere;
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
                this.position[0] == atom.position[0] &&
                this.position[1] == atom.position[1] &&
                this.position[2] == atom.position[2];
    }

    @Override
    public int hashCode() {
        return 31 * (int)this.position[0] + 313 * (int)this.position[1] + 3131 * (int)this.position[2] + 61 * atomicNumber + 23 * charge + 13 * neutrons;
    }
}