package com.bramerlabs.molecular.molecule.atom;

public class Bond {

    private Atom a1, a2;

    private float length;
    private int num; // single 1, double 2, triple 3, etc.

    /**
     * default constructor
     */
    public Bond() {

    }

    /**
     * constructor for 2 atoms
     * @param a1 - the first atom
     * @param a2 - the second atom
     */
    public Bond(Atom a1, Atom a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    /**
     * getter method
     * @return - the first atom
     */
    public Atom getA1() {
        return this.a1;
    }

    /**
     * getter method
     * @return - the second atom
     */
    public Atom getA2() {
        return this.a2;
    }

    /**
     * getter method
     * @return - the float[x, y, z] position of the first atom
     */
    public float[] getA1Pos() {
        return this.a1.getPosition();
    }

    /**
     * getter method
     * @return - the float[x, y, z] position of the second atom
     */
    public float[] getA2Pos() {
        return this.a2.getPosition();
    }

}
