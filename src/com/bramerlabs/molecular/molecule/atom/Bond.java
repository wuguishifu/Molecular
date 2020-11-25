package com.bramerlabs.molecular.molecule.atom;

public class Bond {

    private Atom a1, a2;

    private float length;
    private int num; // single 1, double 2, triple 3, etc.

    public Bond() {

    }

    public Bond(Atom a1, Atom a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    public Atom getA1() {
        return this.a1;
    }

    public Atom getA2() {
        return this.a2;
    }

    public float[] getA1Pos() {
        return this.a1.getPosition();
    }

    public float[] getA2Pos() {
        return this.a2.getPosition();
    }

}
